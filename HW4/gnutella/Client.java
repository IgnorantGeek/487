package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client extends Thread
{
    Socket server;
    DataInputStream in;
    DataOutputStream out;
    private String address;
    private int port;

    public Client(String address, int port)
    {
        // Store the variables
        this.address = address;
        this.port = port;
    }

    public void run()
    {
        try
        {
            server = new Socket(address, port);
            in = new DataInputStream(server.getInputStream());
            out = new DataOutputStream(server.getOutputStream());

            // Connection has been made
        } 
        catch (Exception e)
        {
            //TODO: handle exception
        }
    }
}