package src.manager;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class LightBulbServer
{
	public static void main(String[] args)
	{
		try
		{
			// Create the RMI object
			RMILightBulbImpl obj = new RMILightBulbImpl();

			// Export and initialize the object
			RMILightBulb stub = (RMILightBulb) UnicastRemoteObject.exportObject(obj, 0);

			// Objectify the RMI registry
			Registry registry = LocateRegistry.getRegistry();
			
			// Bind the command to a string
			registry.bind("Light", stub);
			
			// Output server ready, and wait
			System.err.println("Server ready");
			while (true)
			{
				// keep the server live
			}
		}
		catch (Exception e)
		{
			System.err.println ("Error - " + e);
		}
	}
}
	


