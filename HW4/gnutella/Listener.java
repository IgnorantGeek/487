package gnutella;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Listener extends Thread
{
    ServerSocket server;
    Socket client;
    public String ID;
    public String address;
    public int Port;
    public ArrayList<Connector> Neighbors;

    public Listener(int Port, String ID, String address, ArrayList<Connector> Neighbors) throws Exception
    {
        this.ID = ID;
        server = new ServerSocket(Port);
        this.Port = Port;
        this.address = address;
        this.Neighbors = Neighbors;
        System.out.println("Server socket created on port : " + Port);
    }

    public void run()
    {
        try 
        {
            // Listen for connections
            while (true)
            {
                client = server.accept();
                
                Connector connector = new Connector(address, Port, ID, Neighbors, client, Macro.INCOMING); 

                if (Neighbors.size() < 7)
                {
                    connector.start();
                    System.out.println("Client connected.");
                }
            }
        } 
        catch (Exception e)
        {
            // Handle exception
        }
    }
}
