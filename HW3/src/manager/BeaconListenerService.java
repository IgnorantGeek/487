package src.manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BeaconListenerService extends Remote
{
    public int deposit() throws RemoteException;
}