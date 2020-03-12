package src.manager;

import src.misc.Beacon;

public class BeaconListenerImp implements BeaconListener
{
    public BeaconListenerImp() throws java.rmi.RemoteException
    {
        // need a reference to the place where we store the beacons
    }

    public int deposit(Beacon b) throws java.rmi.RemoteException
    {

        return 1;
    }
}