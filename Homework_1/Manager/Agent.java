package Homework_1.Manager;

public class Agent
{
    // Holds the beacon
    public UdpBeacon beacon;

    // Last contact with the client
    public long checkIn;

    public Agent(UdpBeacon beacon)
    {
        this.beacon = beacon;
        checkIn = System.currentTimeMillis();
    }

    public void printAgent()
    {
        beacon.print();
        System.out.println("Last contact with client: " + checkIn);
    }

    public void updateCheckIn()
    {
        this.checkIn = System.currentTimeMillis();
    }
}