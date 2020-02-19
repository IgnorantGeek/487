package Homework_1.Manager;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Agent> agents = new ArrayList<Agent>();
        AgentMonitor monitor = new AgentMonitor(agents);
        BeaconListener listener = new BeaconListener(agents);

        // This is the idea
        monitor.start();
        listener.start();
    }

    public static int toInteger(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getInt();
    }
}