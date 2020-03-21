package src.manager;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LightBulbClient
{
	private LightBulbClient(){}

	public static void main(String[] args)
	{
		String host = (args.length < 1) ? null : args[0];
		try
		{
			// Objectify the RMI registry
			Registry registry = LocateRegistry.getRegistry(host);

			// Lookup and create the remote object
			RMILightBulb stub = (RMILightBulb) registry.lookup("Light"); 

			// Invoke remote object commands...
			stub.on();
			System.out.println ("Bulb state : " + stub.isOn()  );
			System.out.println ("Invoking stub.off()");
			stub.off();
			System.out.println ("Bulb state : " + stub.isOn() );
		}
		// Catch Exceptions
		catch (NotBoundException nbe)
		{
			System.out.println ("Could not find the specified command in the registry.");
		}
		catch (RemoteException re)
		{
			System.out.println ("RMI - " + re);  
		}
		catch (Exception e)
		{
			System.out.println ("Error - " + e); 
		}
	}
}