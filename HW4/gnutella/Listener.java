package gnutella;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Listener extends Thread
{
    ServerSocket server;
    Socket client;
    public String ID;
    public ArrayList<GnutellaNode> Neighbors;

    public Listener(int Port, String ID, ArrayList<GnutellaNode> Neighbors) throws Exception
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
    
    private class clientHandler implements Runnable
    {
        private final Socket clientSocket;
        private DataInputStream in = null;
        private DataOutputStream out = null;

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

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                // We need to create a header and write that to the stream.
                // This is a brand new connection. So write the intial request
                // String : GNUTELLA CONNECT/<protocol version string>\n\n


                // Read back the expected message.

                byte[] back = new String("GNUTELLA OK\n\n").getBytes();

                in.readFully(back);

                // PING the connection. Get a list of neighbors, and their number of connections.
                // Decide whether to connect to nearby nodes by connection number

                Header ping = new Header(ID, Macro.PING, 3, 0, 0);
                byte[] ping_byte = ping.serialize();

                out.write(ping_byte, 0, ping_byte.length);
                System.out.print("PING sent to client.");

                // wait for pong back
                byte[] pong_byte = new byte[23];

                in.readFully(pong_byte);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        
    }
}
