package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GetLocalTime
{
    c_int time;
    c_char valid;

    public GetLocalTime() 
    {
        this.time = new c_int();
        this.valid = new c_char();
    }

    public void execute(String IP, int Port)
    {
        // Create the buffer to contact C server
        int length = time.getSize() + valid.getSize();
        byte[] buffer = new byte[104+length];
        String id = "GetLocalTime";
        for (int i = 0; i < 100; i++)
        {
            if (i < id.length())
            {
                buffer[i] = (byte) id.charAt(i);
            }
            else buffer[i] = (byte) '\0';
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
            System.out.println(e.getMessage());
        }
    }
}