package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GetLocalOS
{
    c_char[] OS;
    c_char valid;

    public GetLocalOS()
    {
        this.OS = new c_char[16];
        this.valid = new c_char();
    }

    public void execute(String IP, int Port)
    {
        // Create the buffer to contact C server
        int length = OS.length + valid.getSize();
        byte[] header = new byte[104];
        byte[] buffer = new byte[length];

        // Set valid bit for checking
        buffer[16] = -1;

        // Create the command ID
        String id = "GetLocalOS";

        // Initialize the header
        for (int i = 0; i < 100; i++)
        {
            if (i < id.length())
            {
                header[i] = (byte) id.charAt(i);
            }
            else header[i] = (byte) '0';
        }
        byte[] size = Defaults.toBytes(length);
        for (int i = 0; i < 4; i++)
        {
            header[100+i] = size[i];
        }
        
        // Try to contact the server
        try
        {
            Socket server = new Socket(IP, Port);

            DataInputStream inStream = new DataInputStream(server.getInputStream());
            DataOutputStream outStream = new DataOutputStream(server.getOutputStream());

            // Write the buffer and flush stream
            outStream.write(header, 0, header.length);
            outStream.write(buffer, 0, buffer.length);
            outStream.flush();

            // Read back the buffer with the payload
            byte[] payload = new byte[104+length];
            inStream.readFully(payload);
            
            this.valid.setValue(payload[payload.length-1]);

            System.out.println(this.valid);

            // Close the socket
            server.close();
            inStream.close();
            outStream.close();
        }
        catch (Exception e)
        {
            // Do nothing
        }
    }
}