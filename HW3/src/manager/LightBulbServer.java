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
			RMILightBulbImpl obj = new RMILightBulbImpl();
			RMILightBulb stub = (RMILightBulb) UnicastRemoteObject.exportObject(obj, 0);
			//RemoteRef location = bulbService.getRef();
			//System.out.println (location.remoteToString());
			
			// Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Light", stub);
			/*String registry = "localhost"; // where the registry server locates   
			if (args.length >=1) {    
				registry = args[0];   
				}   */
			//String registration = "rmi://" + registry + "/RMILightBulb";   
		//	Naming.rebind( registration, bulbService );
			System.err.println("Server ready");
			while (true)
			{
				if (stub.isOn()) System.out.println("Fuck yeah");
			}
		} 
		catch (Exception e)
		{
			System.err.println ("Error - " + e);
		}
	}
}
	


