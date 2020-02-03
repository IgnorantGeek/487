package Homework_1.Java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer
{
    public static void main(String[] args) throws IOException
    {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        DatagramSocket socket = new DatagramSocket(51716);
        socket.receive(packet); 
        byte[] data = packet.getData(); 
        String s = new String(data, 0, packet.getLength()); 
        System.out.println("Port: " +  packet.getPort()  + 
                                    " on "  +  packet.getAddress()  + 
                                    " sent this message:"); 
        System.out.println(s);

        socket.close();
    }
}