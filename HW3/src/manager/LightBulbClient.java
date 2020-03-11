package src.manager;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LightBulbClient
{
	
	private LightBulbClient(){}

	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		try { 
			/*String registry = "localhost"; // the registry server's IP   
			if (args.length >=1) { 
				registry = args[0]; 
				}*/
		//	String registration = "rmi://" + registry + "/RMILightBulb";
		//	Remote remoteService = Naming.lookup(registration);
			Registry registry = LocateRegistry.getRegistry(host);
			RMILightBulb stub = (RMILightBulb) registry.lookup("Light");
			//RMILightBulb bulbService = (RMILightBulb)remoteService;   
			stub.on();   
			System.out.println ("Bulb state : " + stub.isOn()  );
			System.out.println ("Invoking stub.off()");   
			stub.off();   
			System.out.println ("Bulb state : " + stub.isOn() );  
			} catch (NotBoundException nbe) {   
				System.out.println ("No light bulb service available in registry!");  
			} catch (RemoteException re) { 
				System.out.println ("RMI - " + re);  
			} catch (Exception e) { 
				System.out.println ("Error - " + e); 
				}
		}

	}


