package src.manager;

import java.util.List;

import src.misc.Beacon;

public class RMIBeaconListenerImp implements RMIBeaconListener
{
    private List<Beacon> beacons;
    private List<Long> lastContact;

    public RMIBeaconListenerImp(List<Beacon> beacons, List<Long> lastContact) throws java.rmi.RemoteException
    {
        // Reference to a shared list to store beacons
        this.beacons = beacons;
        this.lastContact = lastContact;
    }

    public int deposit(Beacon b) throws java.rmi.RemoteException
    {
        boolean newBeacon = true;
        for (int i = 0; i < beacons.size(); i++)
        {
            if (b.getID() == beacons.get(i).getID())
            {
                newBeacon = false;
                lastContact.set(i, System.currentTimeMillis());
            }
        }
        if (newBeacon) 
        {
            beacons.add(b);
            lastContact.add(System.currentTimeMillis());
        }
        return 0;
    }
}