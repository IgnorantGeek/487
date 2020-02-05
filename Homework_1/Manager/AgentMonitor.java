package Homework_1.Manager;

import java.util.ArrayList;

public class AgentMonitor extends Thread
{
    ArrayList<UdpBeacon> agents;

    public AgentMonitor(ArrayList<UdpBeacon> agents)
    {
        this.agents = agents;
    }

    public void run()
    {
        try
        {
            // Run here
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}