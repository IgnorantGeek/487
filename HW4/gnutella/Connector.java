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
            byte[] payload = new byte[22];
            payload = new String("GNUTELLA CONNECT/0.4\n\n").getBytes();

            // Expected : GNUTELLA OK\n\n
            byte[] return_payload = new byte[13];

            out.write(payload, 0, payload.length);
            out.flush();

            System.out.println("Payload sent.");

            in.readFully(return_payload);

            String return_str = new String(return_payload);

            if (return_str.compareTo("GNUTELLA OK\n\n") == 0)
            {
                // successful connection made
                System.out.println("Connection established");
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}