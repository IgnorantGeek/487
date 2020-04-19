package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class clientHandler extends Thread
    {
        final Socket clientSocket;
        DataInputStream in = null;
        DataOutputStream out = null;

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

                String connectString = new String("GNUTELLA CONNECT/0.4\n\n");
                byte[] read = connectString.getBytes();

                in.readFully(read);

                if (new String(read).compareTo(connectString) != 0) 
                {
                    // ruh roh
                    in.close();
                    out.close();
                    clientSocket.close();
                    this.interrupt();
                }

                byte[] back = new String("GNUTELLA OK\n\n").getBytes();

                out.write(back, 0, back.length);

                // PING the connection. Get a list of neighbors, and their number of connections.
                // Decide whether to connect to nearby nodes by connection number

                // Header ping = new Header(ID, Macro.PING, 3, 0, 0);
                // byte[] ping_byte = ping.serialize();

                // out.write(ping_byte, 0, ping_byte.length);
                // System.out.print("PING sent to client.");

                // wait for pong back
                byte[] pong_byte = new byte[23];

                in.readFully(pong_byte);
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
        
    }