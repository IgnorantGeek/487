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
    public List<Beacon> beacons;

    public Manager(List<Beacon> ref)
    {
        this.beacons = ref;
    }

    public void run()
    {
        try
		{
            int beaconNum = 0;
            while (true)
            {
				// keep the server live
				if (this.beacons.size() > beaconNum)
				{
                    System.out.println("New beacon recieved.");
                    
                    Registry registry = LocateRegistry.getRegistry();

                    CmdAgent agent = (CmdAgent) registry.lookup(beacons.get(beaconNum).getCmdAgentID());

                    GetLocalTime lt = new GetLocalTime();
                    GetLocalOS os = new GetLocalOS();

                    lt = (GetLocalTime) agent.execute("GetLocalTime", lt);
                    os = (GetLocalOS) agent.execute("GetLocalOS", os);

                    System.out.println("Local Time from agent: " + lt.getTime());
                    System.out.println("Local OS from agent: " + new String(os.getOS()));

					beaconNum++;
				}
				sleep(5000);
            }

		}
		catch (Exception e)
		{
			System.err.println ("Manager Error - " + e);
		}
    }
}