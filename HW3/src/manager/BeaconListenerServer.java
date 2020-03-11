package src.manager;

import java.rmi.Naming;
import java.rmi.RemoteException;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
// import java.rmi.server.UnicastRemoteObject;

public class BeaconListenerServer extends BeaconListenerImpl
{
    public BeaconListenerServer() throws RemoteException { }
    public static void main(String args[])
    {
        try
        {
            BeaconListenerImpl bl = new BeaconListenerImpl();

            Naming.rebind("rmi:///BeaconListener", bl);

            // BeaconListenerService stub = (BeaconListenerService) UnicastRemoteObject.exportObject(bl, 0);

            // Registry reg = LocateRegistry.getRegistry();

            // reg.bind("BeaconListener", stub);
        }
        catch (Exception e) 
        {
            System.err.println("Server exception: " + e.toString()); 
            e.printStackTrace();
        }
    }
}