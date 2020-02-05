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
            byte[] buf = new byte[4];
            for (int i = 0; i < 4; i++)
            {
                buf[i] = data[i];
            }
            int BeaconID = TcpClient.toInteger(buf);
            System.out.println("Port: " +  packet.getPort()  +
                                        " on "  +  packet.getAddress()  +
                                        "\nBeacon recieved with ID: " + BeaconID);

            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}