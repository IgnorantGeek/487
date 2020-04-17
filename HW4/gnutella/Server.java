package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    ServerSocket server;
    Socket client;
    DataInputStream in;
    DataOutputStream out;

    public Server(int Port) throws Exception
    {
        server = new ServerSocket(Port);
    }

    public void run()
    {
        try 
        {
            client = server.accept();
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            // Incoming connection recieved
        } 
        catch (Exception e)
        {
            // Handle exception
        }
    }
}