package src.manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BeaconListenerImpl extends UnicastRemoteObject implements BeaconListenerService
{
    public BeaconListenerImpl() throws RemoteException { }
    public int deposit()
    {
        return 1;
    }
}