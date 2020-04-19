package gnutella;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    ServerSocket server;
    Socket client;

    public Server(int Port) throws Exception
    {
        server = new ServerSocket(Port);
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

                Runnable ch = new clientHandler(client);

                new Thread(ch).start();

                System.out.println("Client connected.");

                // Incoming connection recieved
            }
        } 
        catch (Exception e)
        {
            // Handle exception
        }
    }
    
    public class clientHandler implements Runnable
    {
        private final Socket clientSocket;

        public clientHandler(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run()
        {
            try
            {
                System.out.println("Handling incoming connection....");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String nextline;
                while ((nextline = in.readLine())!=null) {
                System.out.println(nextline);
                }

                System.out.println("Connection handled.");

                clientSocket.close();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        
    }
}
