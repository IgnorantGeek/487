package Homework_1.Manager;

import java.io.*;
import java.net.*;

public class CmdAgent extends Thread
{
    public void run()
    {
        try
        {
            String servIP = "127.0.0.1";

            Socket clientSocket = new Socket(servIP, 51717);

            DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());

            String message = "Message to send to server.";

            byte[] buf = message.getBytes();
            byte[] bufLengthInBinary = toBytes(buf.length);
            
            // send 4 bytes
            outStream.write(bufLengthInBinary, 0, bufLengthInBinary.length);
            // send the string
            outStream.write(buf, 0, buf.length);
            outStream.flush();
            // read the data back
            inStream.readFully(bufLengthInBinary);
            byte[] buf2 = new byte[Main.toInteger(bufLengthInBinary)];
            System.out.println(Main.toInteger(bufLengthInBinary));
            inStream.readFully(buf2);
            // convert the binary bytes to string
            String ret = new String(buf2);
            // should be all upcases now
            System.out.println("Message returned from server: " + ret);

            inStream.close();
            outStream.close();
            clientSocket.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void printBinaryArray(byte[] b, String comment)
    {
        System.out.println(comment);
        for (int i=0; i<b.length; i++)
        {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }

    static private byte[] toBytes(int i)
    {
        byte[] result = new byte[4];
        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);
        return result;
    }
}