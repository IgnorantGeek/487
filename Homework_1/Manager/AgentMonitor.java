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
            int currentSize = 0;
            while(true)
            {
                if (agents.size() > currentSize)
                {
                    // do the thing, we have a new thread.
                }
                else
                {
                    // what do we do? how do we check for a dead thread?
                    // what if it just so happens that two agents are sending beacons at the same time?
                    // if the intervals are the same, one of those beacons will always be sent within the time
                    // that it takes to process the other one, meaning that there could be a case where a beacon
                    // is always missed. Might need to ask about this scenario.
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}