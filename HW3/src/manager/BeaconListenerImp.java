package src.manager;

import java.util.List;

import src.misc.Beacon;

public class BeaconListenerImp implements BeaconListener
{
    private List<Beacon> beacons;

    public BeaconListenerImp(List<Beacon> ref) throws java.rmi.RemoteException
    {
        // Reference to a shared list to store beacons
        this.beacons = ref;
    }

    public int deposit(Beacon b) throws java.rmi.RemoteException
    {
        this.beacons.add(b);
        return 0;
    }
}