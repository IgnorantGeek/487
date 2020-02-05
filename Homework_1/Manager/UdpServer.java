package Homework_1.Manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer extends Thread
{
    public static void main(String[] args)
    {
        try
        {    
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(51716);
            socket.receive(packet);
            byte[] data = packet.getData();
            byte[] ID = new byte[4];
            for (int i = 0; i < 4; i++)
            {
                ID[i] = data[i];
            }
            int s = TcpClient.toInteger(ID);
            System.out.println("Port: " +  packet.getPort()  +
                                        " on "  +  packet.getAddress()  +
                                        " beacon recieved with ID: ");
            System.out.println(s);

            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}