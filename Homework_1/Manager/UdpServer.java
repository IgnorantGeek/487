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
            for (int i = 0; i < 4; i++)
            {
                buf[i] = data[i+4];
            }
            int StartupTime = TcpClient.toInteger(buf);
            for (int i = 0; i < 4; i++)
            {
                buf[i] = data[i+8];
            }
            int Interval = TcpClient.toInteger(buf);
            int[] ip = new int[4];
            for (int i = 0; i < 4; i++)
            {
                // This deals with unsigned bytes on java end
                if (data[i+12] >= 0) ip[i] = data[i+12];
                else ip[i] = 256 + data[i+12];
            }
            for (int i = 0; i < 4; i++)
            {
                buf[i] = data[i+16];
            }
            int cmdPort = TcpClient.toInteger(buf);
            UdpBeacon beacon = new UdpBeacon(BeaconID, StartupTime, Interval, cmdPort, ip);
            System.out.println("Packets recieved at Port: " +  packet.getPort()  +
                                        " on "  +  packet.getAddress());

            beacon.print();

            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}