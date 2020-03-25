package src.manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import src.agent.CmdAgent;
import src.misc.Beacon;
import src.misc.GetLocalOS;
import src.misc.GetLocalTime;

public class Manager extends Thread
{
    private List<Beacon> beacons;
    private List<Long> lastContact;

    public Manager(List<Beacon> beacons, List<Long> lastContact)
    {
        this.beacons = beacons;
        this.lastContact = lastContact;
    }

    public void run()
    {
        try
		{
            int beaconNum = 0;
            while (true)
            {
                // Check for new beacons
				if (this.beacons.size() > beaconNum)
				{
                    System.out.println("BEACON RECIEVED - ID: " + beacons.get(beaconNum).getID());
                    
                    Registry registry = LocateRegistry.getRegistry();

                    CmdAgent agent = (CmdAgent) registry.lookup(beacons.get(beaconNum).getCmdAgentID());

                    GetLocalTime lt = new GetLocalTime();
                    GetLocalOS os = new GetLocalOS();

                    lt = (GetLocalTime) agent.execute("GetLocalTime", lt);
                    os = (GetLocalOS) agent.execute("GetLocalOS", os);

                    System.out.println("Local Time from agent: " + lt.getTime());
                    System.out.println("Local OS from agent: " + new String(os.getOS()) + "\n");

                    beaconNum++;
                }

                // Check for dead agents
                for (int i = 0; i < lastContact.size(); i++)
                {
                    if (System.currentTimeMillis() - lastContact.get(i) > 120000)
                    {
                        System.out.println("Agent with ID: " + beacons.get(i).getID() + " has not sent a beacon in 120 s. Agent considered dead.\n");
                        lastContact.remove(i);
                        beacons.remove(i);
                        beaconNum--;
                        break;
                    }
                }
				sleep(2000);
            }

		}
		catch (Exception e)
		{
			System.err.println ("Manager Error - " + e);
		}
    }
}