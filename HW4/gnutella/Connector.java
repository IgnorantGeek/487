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
    private ArrayList<Connector> Neighbors;
    public Neighbor neighbor;
    private int neighborCount = 0;
    private int function = -1;


    public Connector(String address, int port, String ID, ArrayList<Connector> Neighbors, int initialFunction)
    {
        this.address = address;
        this.Port = port;
        this.ID = ID;
        this.Neighbors = Neighbors;
        function = initialFunction;
    }

    public Connector(String address, int port, String ID, ArrayList<Connector> Neighbors, Socket socket, int initialFunction)
    {
        this.address = address;
        this.ID = ID;
        this.socket = socket;
        this.Port = port;
        this.Neighbors = Neighbors;
        function = initialFunction;
    }

    public Connector(Connector connector)
    {
        socket = connector.socket;
        in = connector.in;
        out = connector.out;
        address = connector.address;
        ID = connector.ID;
        Port = connector.Port;
        numFiles = connector.numFiles;
        Neighbors = connector.Neighbors;
        neighborCount = connector.neighborCount;
        neighbor = connector.neighbor;
        function = connector.function;
    }

    public void setFunction(int function)
    {
        this.function = function;
    }

    public void run()
    {
        try
        {
            // Initial connection, made by outgoing call
            if (function == Macro.OUTGOING)
            {
                System.out.println("Handling outgoing connection...");
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
                    
                    SendPing();

                    byte[] pong_byte = new byte[23];

                    System.out.println("PING sent. Waiting for PONG.");

                    in.readFully(pong_byte);

                    System.out.println("HEADER received.");

                    Header pong = Header.deserialize(pong_byte);

                    if (pong.pl_descriptor == Macro.PONG)
                    {
                        System.out.println("PONG RECIEVED.");
                        // read the pong payload
                        byte[] payload = new byte[pong.pl_length];
                        
                        in.readFully(payload);

                        Neighbor neighbor = Neighbor.decodePong(payload);

                        this.neighbor = neighbor;

                        // Insert neighbor into array
                        Neighbors.add(this);

                        // decide whether to contact new agents
                    }
                    neighborCount = Neighbors.size();
                    System.out.println("Neighbor count: " + neighborCount); 
                    System.out.println("PONG PROCESSED. WAITING");
                }
            }
            // Initial connection. made by incoming connection
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

                    SendPong(ping);
                }
            }
            // The connector has a waiting message, read the message
            else if (function == Macro.READ)
            {
                Header header = readHeader();

                if (header.pl_descriptor == Macro.PING)
                {
                    // pong back and update neighbor contact time
                    SendPong(header);
                }
                else if (header.pl_descriptor == Macro.QUERY)
                {
                    // check for query, if we have already seen this query, remove and don't forward it
                }
                else if (header.pl_descriptor == Macro.QUERYHIT)
                {
                    // Back propagate the queryhit message, but check for an existing query id
                }
                else if (header.pl_descriptor == Macro.PONG)
                {
                    // probably shouldn't get a random pong, discard from network
                }
            }
            // The connector needs to ping its neighbor
            else if (function == Macro.PING)
            {
                System.out.println("Sending PING....");
                SendPing();

                Header pong = readHeader();

                byte[] pong_payload = readPong(pong);

                Neighbor n = Neighbor.decodePong(pong_payload);

                // check for the neighbor
                if (this.neighbor.ID.equals(n.ID))
                {
                    this.neighbor = n;
                }
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

    public Header SendPing()
    {
        try
        {
            // send a ping to this particular connection
            Header ping = new Header(ID, Macro.PING, 1, 0, 0);

            byte[] ping_byte = ping.serialize();

            out.write(ping_byte, 0, ping_byte.length);
            out.flush();

            return ping;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Header readHeader()
    {
        try
        {
            Header h = new Header();
            byte[] data = new byte[23];

            in.readFully(data);

            h = Header.deserialize(data);

            return h;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] readPong(Header pongHeader)
    {
        try
        {
            byte[] payload = new byte[pongHeader.pl_length];

            in.readFully(payload);

            return payload;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void SendPong(Header header)
    {
        try
        {
            Header pong = new Header(ID, Macro.PONG, 1, 0, 28 + 8 * neighborCount);

            byte[] header_byte = pong.serialize();

            out.write(header_byte, 0, header_byte.length);

            byte[] pong_byte = this.EncodePongBytes();

            // If this neighbor is already in the list, don't add a new neighbor
            boolean new_neighbor = true;
            for (Connector c : Neighbors)
            {
                Neighbor n = c.neighbor;
                if (n.ID.equals(header.ID))
                {
                    // This is not a new neighbor we are ponging, update system time
                    new_neighbor = false;
                    n.lastContact = System.currentTimeMillis();
                    break;
                }
            }
            if (new_neighbor && this.neighborCount < 7)
            {
                // add this thread and a neighbor
                Neighbor neighbor = new Neighbor();
                neighbor.ID = header.ID;
                neighbor.lastContact = System.currentTimeMillis();
                this.neighbor = neighbor;
                Neighbors.add(this);
            }

            out.write(pong_byte, 0, pong_byte.length);
            out.flush();

            System.out.println("Pong sent. Byte  length : " + pong_byte.length);
            neighborCount = Neighbors.size();
            System.out.println("Neighbor count: " + neighborCount);
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
            Neighbor friend = Neighbors.get(i).neighbor;
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