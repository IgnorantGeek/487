package src.manager;

import java.rmi.Remote;
import src.misc.Beacon;

public interface BeaconListener extends Remote
{
    public int deposit(Beacon b) throws java.rmi.RemoteException;
}