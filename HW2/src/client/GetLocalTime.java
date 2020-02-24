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
        byte[] header = new byte[104];
        byte[] buffer = new byte[length];

        // Set valid bit for checking
        buffer[4] = -1;

        // Create the command ID
        String id = "GetLocalTime";

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

            byte[] timeByte = new byte[4];

            for (int i = 0; i < 4; i++)
            {
                timeByte[i] = payload[104+i];
            }

            // Set the values
            this.time.setValue(timeByte);
            this.valid.setValue(payload[payload.length-1]);

            System.out.println("System time: " + this.time.getValue());
            System.out.println("Valid flag: " + this.valid.getValue());

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