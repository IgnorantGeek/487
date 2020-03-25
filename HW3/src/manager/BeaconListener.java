package src.manager;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import src.misc.Beacon;


public class BeaconListener extends Thread
{
	private List<Beacon> beacons;
	private List<Long> lastContact;

	public BeaconListener(List<Beacon> beacons, List<Long> lastContact)
	{
		this.beacons = beacons;
		this.lastContact = lastContact;
	}

	public void run()
	{
		try
		{
			// Create the RMI object
			RMIBeaconListenerImp obj = new RMIBeaconListenerImp(beacons, lastContact);

			// Export and initialize the object
			RMIBeaconListener stub = (RMIBeaconListener) UnicastRemoteObject.exportObject(obj, 0);

			// Objectify the RMI registry
			Registry registry = LocateRegistry.getRegistry();
			
			// Bind the command to a string
			registry.bind("BeaconListener", stub);
			
			// Output server ready, and wait
			System.err.println("Server ready");
			
			// while (true)
			// {
			// 	// do we need to keep the server live?
			// }
		}
		catch (Exception e)
		{
			System.err.println ("Beacon Listener Error - " + e);
		}
	}
}