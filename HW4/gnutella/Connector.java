package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connector extends Thread
{
    Socket server;
    DataInputStream in;
    DataOutputStream out;
    private String address;
    private String ID;
    private int port;
    private ArrayList<Pair<Neighbor, Thread>> Neighbors;


    public Connector(String address, int port, String ID, ArrayList<Pair<Neighbor, Thread>> Neighbors)
    {
        // Store the variables
        this.address = address;
        this.port = port;
        this.ID = ID;
        this.Neighbors = Neighbors;
    }

    public void run()
    {
        try
        {
            System.out.println("Establishing Connection at - " + address + ":" + port);
            server = new Socket(address, port);
            in = new DataInputStream(server.getInputStream());
            out = new DataOutputStream(server.getOutputStream());

            // Connection has been made
            byte[] connect_payload = new byte[22];
            connect_payload = new String("GNUTELLA CONNECT/0.4\n\n").getBytes();

            // Expected : GNUTELLA OK\n\n
            byte[] return_payload = new byte[13];

            out.write(connect_payload, 0, connect_payload.length);
            out.flush();

            System.out.println("Payload sent.");

            in.readFully(return_payload);

            String return_str = new String(return_payload);

            if (return_str.compareTo("GNUTELLA OK\n\n") == 0)
            {    
                // successful connection made
                Header ping = new Header(ID, Macro.PING, 1, 0, 0);
                byte[] serial_ping = ping.serialize();

                out.write(serial_ping, 0, serial_ping.length);
                out.flush();

                byte[] pong_byte = new byte[23];

                in.readFully(pong_byte);

                Header pong = Header.deserialize(pong_byte);

                if (pong.pl_descriptor == Macro.PONG)
                {
                    Neighbor neighbor = new Neighbor();

                    // read the pong payload
                    byte[] payload = new byte[pong.pl_length];
                    
                    in.readFully(payload);


                    byte[] buf = new byte[16];
                    for (int i = 0; i < buf.length; i++)
                    {
                        buf[i] = payload[i];
                    }

                    neighbor.ID = new String(buf);

                    buf = new byte[2];
                    buf[0] = payload[16];
                    buf[1] = payload[17];
                    neighbor.Port = Macro.toInteger(buf);

                    int ip1 = payload[18] & 0xff;
                    int ip2 = payload[19] & 0xff;
                    int ip3 = payload[20] & 0xff;
                    int ip4 = payload[21] & 0xff;
                    neighbor.IP = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

                    buf = new byte[4];
                    for (int i = 0; i < buf.length; i++)
                    {
                        buf[i] = payload[22+i];
                    }
                    neighbor.NumFiles = Macro.toInteger(buf);
                    
                    for (int i = 0; i < buf.length; i++)
                    {
                        buf[i] = payload[26+i];
                    }
                    neighbor.SharedSize = Macro.toInteger(buf);

                    buf = new byte[1];

                    buf[0] = payload[30];
                    neighbor.friendCount = Macro.toInteger(buf);

                    // need to get the ips of the neighbors
                    for (int i = 0; i < neighbor.friendCount; i++)
                    {
                        Neighbor friend = new Neighbor();
                        byte[] port_buf = new byte[2];

                        // Port read section
                        port_buf[0] = payload[31+7*i];
                        port_buf[1] = payload[32+7*i];
                        friend.Port = Macro.toInteger(port_buf);

                        // ip read section
                        int friend_ip1 = payload[33+7*i] & 0xff;
                        int friend_ip2 = payload[34+7*i] & 0xff;
                        int friend_ip3 = payload[35+7*i] & 0xff;
                        int friend_ip4 = payload[36+7*i] & 0xff;
                        neighbor.IP = friend_ip1 + "." + friend_ip2 + "." + friend_ip3 + "." + friend_ip4;

                        neighbor.friendCount = payload[37+7*i] & 0xff;
                    }

                    // neighbor should be initialized. Need to insert it into array
                    Pair<Neighbor, Thread> pair = new Pair<Neighbor,Thread>(neighbor, this);
                    Neighbors.add(pair);

                    // decide whether to contact new agents
                }

                while (true)
                {
                    byte[] header_byte = new byte[23];

                    in.readFully(header_byte);

                    Header header = Header.deserialize(header_byte);

                    // Switch of the descriptor of the header
                    if (header.pl_descriptor == Macro.PONG)
                    {
                        // read the pong payload
                        byte[] payload = new byte[header.pl_length];
                        
                        in.readFully(payload);


                    }

                }
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}