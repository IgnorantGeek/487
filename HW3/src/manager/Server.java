package src.manager;

import java.util.ArrayList;

import src.misc.Beacon;

public class Server
{
    public static void main(String[] args)
    {
        // Create the beacons arraylist reference
        ArrayList<Beacon> beacons = new ArrayList<Beacon>();

        // Initialize the manager and listener
        Manager manager = new Manager(beacons);
        BeaconListener listener = new BeaconListener(beacons);

        // Run the threads
        manager.start();
        listener.start();
    }
}