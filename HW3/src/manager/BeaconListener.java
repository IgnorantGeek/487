package src.manager;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import src.misc.Beacon;


public class BeaconListener extends Thread
{
	List<Beacon> beacons;

	public BeaconListener(List<Beacon> ref)
	{
		this.beacons = ref;
	}

	public void run()
	{
		try
		{
			// Create the RMI object
			RMIBeaconListenerImp obj = new RMIBeaconListenerImp(beacons);

			// Export and initialize the object
			RMIBeaconListener stub = (RMIBeaconListener) UnicastRemoteObject.exportObject(obj, 0);

			// Objectify the RMI registry
			Registry registry = LocateRegistry.getRegistry();
			
			// Bind the command to a string
			registry.bind("BeaconListener", stub);
			
			// Output server ready, and wait
            System.err.println("Server ready");
			
			int beaconNum = 0;
			while (true)
			{
				// keep the server live
				if (this.beacons.size() > beaconNum)
				{
					System.out.println("New beacon recieved.");
					beaconNum++;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println ("Beacon Listener Error - " + e);
		}
	}
}