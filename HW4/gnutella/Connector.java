package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Connector extends Thread
{
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String address;
    private String ID;
    private int Port;
    private int numFiles;
    private ArrayList<Pair<Neighbor, Thread>> Neighbors;
    private int neighborCount = 0;
    private int function = -1;


    public Connector(String address, int port, String ID, ArrayList<Pair<Neighbor, Thread>> Neighbors, int initialFunction)
    {
        this.address = address;
        this.Port = port;
        this.ID = ID;
        this.Neighbors = Neighbors;
        function = initialFunction;
    }

    public Connector(String address, int port, String ID, ArrayList<Pair<Neighbor, Thread>> Neighbors, Socket socket, int initialFunction)
    {
        this.address = address;
        this.ID = ID;
        this.socket = socket;
        this.Port = port;
        this.Neighbors = Neighbors;
        function = initialFunction;
    }

    public void run()
    {
        try
        {
            // If function is intial Outgoing
            if (function == Macro.OUTGOING)
            {
                System.out.println("Establishing Connection at - " + address + ":" + Port);
                socket = new Socket(address, Port);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

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
                    System.out.println("Connection established. PINGING.");
                    Header ping = new Header(ID, Macro.PING, 1, 0, 0);
                    byte[] serial_ping = ping.serialize();

                    out.write(serial_ping, 0, serial_ping.length);
                    out.flush();

                    byte[] pong_byte = new byte[23];

                    System.out.println("PING sent. Waiting for PONG.");

                    in.readFully(pong_byte);

                    System.out.println("HEADER received.");

                    Header pong = Header.deserialize(pong_byte);

                    if (pong.pl_descriptor == Macro.PONG)
                    {
                        System.out.println("PONG RECIEVED.");
                        System.out.println(pong.pl_length);
                        // read the pong payload
                        byte[] payload = new byte[pong.pl_length];
                        
                        in.readFully(payload);

                        Neighbor neighbor = Neighbor.decodePong(payload);

                        // Insert neighbor into array
                        Pair<Neighbor, Thread> pair = new Pair<Neighbor,Thread>(neighbor, this);
                        Neighbors.add(pair);

                        // decide whether to contact new agents
                    }
                }
            }
            else if (function == Macro.INCOMING)
            {
                System.out.println("Handling incoming connection....");
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                // We need to create a header and write that to the stream.
                // This is a brand new connection. So write the intial request
                // String : GNUTELLA CONNECT/<protocol version string>\n\n


                // Read back the expected message.

                String connectString = new String("GNUTELLA CONNECT/0.4\n\n");
                byte[] read = connectString.getBytes();

                in.readFully(read);

                if (new String(read).compareTo(connectString) != 0) 
                {
                    // ruh roh
                    in.close();
                    out.close();
                    socket.close();
                    this.interrupt();
                }

                byte[] back = new String("GNUTELLA OK\n\n").getBytes();

                out.write(back, 0, back.length);
                
                // Read expected ping

                byte[] ping_byte = new byte[23];

                in.readFully(ping_byte);

                Header ping = Header.deserialize(ping_byte);

                if (ping.pl_descriptor == Macro.PING)
                {


                    System.out.println("PING RECIEVED - " + ping.ID);

                    Header pong = new Header(ID, Macro.PONG, 1, 0, 28 + 8 * neighborCount);

                    byte[] header_byte = pong.serialize();

                    out.write(header_byte, 0, header_byte.length);

                    byte[] pong_byte = this.EncodePongBytes();
                    
                    out.write(pong_byte, 0, pong_byte.length);
                    out.flush();

                    System.out.println("Pong sent. Byte  length : " + pong_byte.length);
                }
                // else, check the list of friends from the neighbor
            }
        } 
        catch (SocketException e)
        {
            // don't do anything
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public byte[] EncodePongBytes()
    {
        byte[] payload = new byte[28 + 8 * neighborCount];

        byte[] buf = ID.getBytes();

        for (int i = 0; i < 16; i++)
        {
            payload[i] = buf[i];
        }

        buf = new byte[2];
        buf[0] = (byte) (this.Port & 0xff);
        buf[1] = (byte) ((this.Port >> 8) & 0xff);

        payload[16] = buf[0];
        payload[17] = buf[1];

        buf = new byte[4];
        buf = Macro.IpTo4Bytes(this.address);
        for (int i = 0; i < 4; i++)
        {
            payload[18+i] = buf[i];
        }

        buf = Macro.to4Bytes(this.numFiles);
        for (int i = 0; i < 4; i++)
        {
            payload[22+i] = buf[i];
        }

        byte fc = (byte) this.neighborCount;
        byte valid;
        if (this.neighborCount < 7) valid = 0x00;
        else valid = 0x01;

        payload[26] = fc;
        payload[27] = valid;

        for (int i = 0; i < neighborCount; i++)
        {
            Neighbor friend = Neighbors.get(i).getLeft();
            byte[] port_buf = new byte[2];

            port_buf[0] = (byte) (this.Port & 0xff);
            port_buf[1] = (byte) ((this.Port >> 8) & 0xff);

            // Port read section
            payload[28+7*i] = port_buf[0];
            payload[29+7*i] = port_buf[1];

            // ip read section
            byte[] ip = new byte[4];
            ip = Macro.IpTo4Bytes(friend.IP);
            payload[30+7*i] = ip[0];
            payload[31+7*i] = ip[1];
            payload[32+7*i] = ip[2];
            payload[33+7*i] = ip[3];

            payload[34+7*i] = (byte) friend.friendCount;
            
        }
        
        return payload;
    }
}