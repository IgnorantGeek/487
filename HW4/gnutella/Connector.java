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
                    // read the pong payload
                    byte[] payload = new byte[pong.pl_length];
                    
                    in.readFully(payload);

                    Neighbor neighbor = Neighbor.decodePong(payload);

                    // Insert neighbor into array
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