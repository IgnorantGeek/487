package gnutella;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Listener extends Thread
{
    ServerSocket server;
    Socket client;
    public String ID;
    public ArrayList<Pair<Neighbor, Thread>> Neighbors;

    public Listener(int Port, String ID, ArrayList<Pair<Neighbor, Thread>> Neighbors) throws Exception
    {
        this.ID = ID;
        server = new ServerSocket(Port);
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
                
                clientHandler ch = new clientHandler(client);

                if (Neighbors.size() < 7)
                {
                    ch.start();
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
