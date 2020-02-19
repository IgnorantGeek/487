package Homework_1.Manager;

import java.io.*;
import java.net.*;

public class CmdAgent extends Thread
{
    UdpBeacon beacon;

    public CmdAgent(UdpBeacon beacon)
    {
        this.beacon = beacon;
    }

    public void run()
    {
        try
        {
            // Initialize and create the socket from the beacon info
            int serverPort = beacon.CmdPort;
            String serverIP = beacon.IP[0] + "." + beacon.IP[1] + "." + beacon.IP[2] + "." + beacon.IP[3];
            System.out.println("MONITOR: Attempting to contact " + serverIP + " on port " + serverPort + "....");
            Socket clientSocket = new Socket(serverIP, serverPort);

            // Initialize input and output streams
            DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("MONITOR: TCP connection created.");

            // Message for server. (To be interpreted on server side)
            String message = "Message to send to server.";

            // Get the length of the message
            byte[] buf = message.getBytes();
            byte[] bufLengthInBinary = toBytes(buf.length);
            
            // Send the length in 4 bytes
            outStream.write(bufLengthInBinary, 0, bufLengthInBinary.length);
            
            // Send the payload and flush stream
            outStream.write(buf, 0, buf.length);
            outStream.flush();

            // Read the data back, starting with the length
            inStream.readFully(bufLengthInBinary);
            
            // Initialize a buffer to load data
            byte[] OsMessageBuffer = new byte[Main.toInteger(bufLengthInBinary)];

            // Read the rest of the message
            inStream.readFully(OsMessageBuffer);

            // convert the binary bytes to string
            int OsValid = -1;
            String OsString = decodeOSData(OsMessageBuffer, OsValid);

            System.out.println("MONITOR: Agent Operating System - " + OsString);

            // Read the length of the localtime message
            inStream.readFully(bufLengthInBinary);

            // Initialize a buffer to next payload
            byte[] TimeMessageBuffer = new byte[Main.toInteger(bufLengthInBinary)];

            // Read the localtime payload from the agent
            inStream.readFully(TimeMessageBuffer);

            int TimeValid = -1;
            int TimeInteger = decodeTimeData(TimeMessageBuffer, TimeValid);

            System.out.println("MONITOR: Agent Local Time (Unix) - " + TimeInteger);

            // Close all streams
            inStream.close();
            outStream.close();
            clientSocket.close();
            System.out.println("MONITOR: TCP Connection closed.");
        }
        catch (Exception e)
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

    static String decodeOSData(byte[] data, int valid)
    {
        byte[] buf = new byte[data.length-4];
        byte[] valid_buf = new byte[4];
        for (int i = 0; i < data.length-4; i++)
        {
            buf[i] = data[i];
        }
        String OS = new String(buf);
        for (int i = 0; i < 4; i++)
        {
            valid_buf[i] = data[data.length-4+i];
        }
        valid = Main.toInteger(valid_buf);
        return OS;
    }

    static int decodeTimeData(byte[] data, int valid)
    {
        byte[] buf = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            buf[i] = data[i];
        }
        int time = Main.toInteger(buf);
        for (int i = 0; i < 4; i++)
        {
            buf[i] = data[i+4];
        }
        valid = Main.toInteger(buf);
        return time;
    }
}