package Homework_1.Manager;

import java.util.ArrayList;

public class AgentMonitor extends Thread
{
    ArrayList<Agent> agents;

    public AgentMonitor(ArrayList<Agent> agents)
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
                for (Agent agent : agents)
                {
                    if (System.currentTimeMillis() - agent.checkIn > ((agent.beacon.Interval*2) * 60000))
                    {
                        // dead agent
                        System.out.println("MONITOR: Agent with ID: " + agent.beacon.ID + " has not sent a beacon in " + agent.beacon.Interval * 2 + " minutes. Agent is considered dead.");
                        agents.remove(agent);
                        currentSize--;
                    }
                }

                // New agent found. Send the TCP command
                if (agents.size() > currentSize)
                {
                    // we might want this send command in a separate thread??
                    agents.get(currentSize).contactAgent();
                    currentSize++;
                }

                // wait 20 seconds and try again. Checks 3 times a minute
                sleep(20000);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}