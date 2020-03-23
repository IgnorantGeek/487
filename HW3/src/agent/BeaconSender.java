package src.agent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import src.manager.RMIBeaconListener;
import src.misc.Beacon;

public class BeaconSender extends Thread
{
    public Beacon beacon;

    public BeaconSender()
    {
        this.beacon = new Beacon();
    }

    public BeaconSender(String CmdAgentID)
    {
        this.beacon = new Beacon(CmdAgentID);
    }

    public void run()
    {
        try
        {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry(null);

            RMIBeaconListener listener = (RMIBeaconListener) registry.lookup("BeaconListener");

            // Repeatedly deposit the beacon
            while (true)
            {
                listener.deposit(this.beacon);

                // Wait a minute and do it again
                sleep(60000);
            }
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