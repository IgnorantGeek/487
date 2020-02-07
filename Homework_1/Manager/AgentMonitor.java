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
            System.out.println("Entering agent loop.");
            while(true)
            {
                for (Agent agent : agents)
                {
                    if (System.currentTimeMillis() - agent.checkIn > ((agent.beacon.Interval*2) * 60000))
                    {
                        // dead agent
                    }
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