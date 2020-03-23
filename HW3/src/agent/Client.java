package src.agent;

public class Client
{
    public static void main(String[] args)
    {
        BeaconSender sender = new BeaconSender();
        
        sender.start();
    }
}