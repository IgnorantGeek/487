package src.manager;

import java.util.List;

import src.misc.Beacon;

public class Manager
{
    public List<Beacon> beacons;

    public Manager(List<Beacon> ref)
    {
        this.beacons = ref;
    }

    public void GetLocalOS()
    {
        // Get the local OS of the client
    }

    public void GetLocalTime()
    {
        // Get the local time of the client
    }
}