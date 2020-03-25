package src.manager;

import java.util.ArrayList;

import src.misc.Beacon;

public class Server
{
    public static void main(String[] args)
    {
        // Create the beacons arraylist reference
        ArrayList<Beacon> beacons = new ArrayList<Beacon>();
        ArrayList<Long> lastContact = new ArrayList<Long>();

        // Initialize the manager and listener
        Manager manager = new Manager(beacons, lastContact);
        BeaconListener listener = new BeaconListener(beacons, lastContact);

        // Run the threads
        listener.start();
        manager.start();
    }
}