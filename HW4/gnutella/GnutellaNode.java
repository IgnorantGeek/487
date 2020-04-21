package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int ListenPort;
    public int ConnectPort;
    public ArrayList<Pair<Neighbor, Connector>> Neighbors = new ArrayList<Pair<Neighbor, Connector>>();

    // Network variables
    Connector connector = null;
    Listener listener   = null;

    public GnutellaNode()
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        ListenPort = Macro.DEFAULTPORT;
    }

    public GnutellaNode(int Port)
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        this.ListenPort = Port;
    }

    public GnutellaNode(int listen, int connect)
    {

        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        this.ListenPort = listen;
        this.ConnectPort = connect;
    }

    public void Connect(String address) throws Exception
    {
        System.out.println("Joing network with ID: " + ID);
        System.out.println("Pinging " + IP + ":" + ConnectPort + " for network config....");
        Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
        listener.start();
        // try to join pre-existing network
        connector = new Connector(address, ConnectPort, ID, Neighbors, Macro.OUTGOING);
        connector.start();

        while (true)
        {
            for (Pair<Neighbor,Connector> pair : Neighbors)
            {
                Connector c = pair.getRight();
                if (c.in.available() != 0)
                {
                    // We have a connection. Need to read the header.
                    System.out.println("DATA AVAILABLE");
                    c.setFunction(Macro.WAITING);
                    c.start();
                } 
            }
        }
    }

    public void Start() throws Exception
    {
        System.out.println("Creating network with root ID: " + ID);
        Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
        listener.start();

        // Wait 20 seconds and check for updates to the neighbors list
        // Check for last contact greater than some number, and valid ip/port
        Thread.sleep(20000);
        
        System.out.println("Checking the pairs");
        while (true)
        {
            for (Pair<Neighbor,Connector> pair : Neighbors)
            {
                Connector c = pair.getRight();
                if (c.in.available() != 0)
                {
                    // We have a connection. Need to read the header.
                    System.out.println("DATA AVAILABLE");
                } 
            }

            for (Pair<Neighbor,Connector> pair : Neighbors)
            {
                if (pair.getLeft().IP == null)
                {
                    System.out.println("PINGING");
                    pair.getRight().setFunction(Macro.PING);
                    pair.getRight().start();
                    Thread.sleep(10000);
                }    
            }
        }
    }
}