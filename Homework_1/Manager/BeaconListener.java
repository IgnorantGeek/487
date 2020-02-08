package Homework_1.Manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class BeaconListener extends Thread
{
    public ArrayList<Agent> agents;

    public BeaconListener(ArrayList<Agent> agents)
    {
        this.agents = agents;
    }

    public void run()
    {
        try
        {
            // Udp server loop
            while (true)
            {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                DatagramSocket socket = new DatagramSocket(51716);
                socket.receive(packet);
                byte[] data = packet.getData();
                UdpBeacon beacon = decodeBeacon(data);
                boolean newBeacon = true;
                for (Agent agent : this.agents)
                {
                    if (agent.beacon.compareTo(beacon))
                    {
                        // Update this check in time
                        agent.updateCheckIn();
                        newBeacon = false;
                        System.out.println("Beacon recieved. Agent arrival time updated.");
                    }
                }
                if (newBeacon)
                {
                    System.out.println("New agent added.");
                    Agent hold = new Agent(beacon);
                    agents.add(hold);
                    
                    //hold.printAgent();
                }
                socket.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public UdpBeacon decodeBeacon(byte[] data)
    {
        byte[] buf = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            buf[i] = data[i];
        }
        int BeaconID = Main.toInteger(buf);
        for (int i = 0; i < 4; i++)
        {
            buf[i] = data[i+4];
        }
        int StartupTime = Main.toInteger(buf);
        for (int i = 0; i < 4; i++)
        {
            buf[i] = data[i+8];
        }
        int Interval = Main.toInteger(buf);
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
        int cmdPort = Main.toInteger(buf);
        return new UdpBeacon(BeaconID, StartupTime, Interval, cmdPort, ip);
    }
}