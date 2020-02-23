package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class GetLocalOS
{
    ArrayList<c_char> OS = new ArrayList<c_char>(16);
    c_char valid;

    public GetLocalOS() { }

    public void execute(String IP, int Port)
    {
        // Create the buffer to contact C server
        int length = OS.size() + valid.getSize();
        byte[] buffer = new byte[104+length];
        String id = "GetLocalOS";
        for (int i = 0; i < 100; i++)
        {
            if (i < id.length())
            {
                buffer[i] = (byte) id.charAt(i);
            }
            else buffer[i] = (byte) 'n';
        }
        byte[] size = Defaults.toBytes(length);
        for (int i = 0; i < 4; i++)
        {
            buffer[100+i] = size[i];
        }
        
        // Try to contact the server
        try
        {
            Socket server = new Socket(IP, Port);

            DataInputStream inStream = new DataInputStream(server.getInputStream());
            DataOutputStream outStream = new DataOutputStream(server.getOutputStream());

            // Write the buffer and flush stream
            outStream.write(buffer, 0, buffer.length);
            outStream.flush();

            // Read back the buffer with the payload
            inStream.readFully(buffer);

            // Decode the buffer


            // Close the socket
            server.close();
        }
        catch (Exception e)
        {
            // System.out.println(e.getMessage());
        }
    }
}