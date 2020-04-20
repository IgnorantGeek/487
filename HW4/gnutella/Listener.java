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
    public ArrayList<Pair<Neighbor, Thread>> Neighbors;

    public Listener(int Port, String ID, String address, ArrayList<Pair<Neighbor, Thread>> Neighbors) throws Exception
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
                System.out.println("Listening for incoming connections....");

                client = server.accept();
                
                Connector connector = new Connector(address, Port, ID, Neighbors, client, Macro.INCOMING); 

                if (Neighbors.size() < 7)
                {
                    connector.start();
                    System.out.println("Client connected.");
                }

                // Incoming connection recieved
            }
        } 
        catch (Exception e)
        {
            // Handle exception
        }
    }
}
