package src.agent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import src.misc.Beacon;

public class CmdReciever extends Thread
{
    private Beacon beacon;

    public CmdReciever(Beacon ref)
    {
        this.beacon = ref;
    }

    public void run()
    {
        try 
        {
            CmdAgentImp obj = new CmdAgentImp();

            CmdAgent stub = (CmdAgent) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();

            registry.bind(beacon.getCmdAgentID(), stub);
        }
		catch (Exception e)
		{
			System.err.println ("CmdReciever Error - " + e);
		}
    }
}