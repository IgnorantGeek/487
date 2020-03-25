package src.agent;

import src.misc.Beacon;

public class Client
{
    public static void main(String[] args)
    {
        Beacon beacon = new Beacon();

        BeaconSender sender = new BeaconSender(beacon);
        CmdReciever reciever = new CmdReciever(beacon);
        
        sender.start();
        reciever.start();
    }
}