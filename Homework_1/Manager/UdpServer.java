package Homework_1.Manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer extends Thread
{
    public void run()
    {
        try
        {    
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(51716);
            socket.receive(packet);
            byte[] data = packet.getData();
            String s = new String(data, 0, packet.getLength());
            System.out.println("Port: " +  packet.getPort()  +
                                        " on "  +  packet.getAddress()  +
                                        " sent this message: ");
            System.out.println(s);

            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}