package src.manager;

import java.util.List;

import src.misc.Beacon;

public class Manager extends Thread
{
    public List<Beacon> beacons;

    public Manager(List<Beacon> ref)
    {
        this.beacons = ref;
    }

    public void GetLocalOS()
    {
        // Get the local OS of the client
    }

    public void GetLocalTime()
    {
        // Get the local time of the client
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