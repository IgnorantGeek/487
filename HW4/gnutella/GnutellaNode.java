package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int ListenPort;
    public int ConnectPort;
    public ArrayList<Connector> Neighbors = new ArrayList<Connector>();

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

    public GnutellaNode(String address)
    {
        ID = Macro.generateString(16);
        IP = address;
        this.ListenPort = Macro.DEFAULTPORT;
    }

    public GnutellaNode(String address, int listen, int connect)
    {

        ID = Macro.generateString(16);
        IP = address;
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
            for (int i = 0; i < Neighbors.size(); i++)
            {
                Connector c = Neighbors.get(i);
                if (c.in.available() != 0)
                {
                    // We have a connection. Need to read the header.
                    System.out.println("DATA AVAILABLE");
                    Connector cn = new Connector(c);
                    cn.setFunction(Macro.READ);
                    Neighbors.set(i, cn);
                    cn.start();
                } 
            }
            Thread.sleep(5000);
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
            for (Connector c : Neighbors)
            {
                if (c.in.available() != 0)
                {
                    // We have a connection. Need to read the header.
                    System.out.println("DATA AVAILABLE");
                } 
            }

            for (int i = 0; i < Neighbors.size(); i++)
            {
                Connector c = Neighbors.get(i);
                if (c.neighbor.IP == null)
                {
                    System.out.println("PINGING");
                    if (!c.isAlive())
                    {
                        Connector cn = new Connector(c);
                        cn.setFunction(Macro.PING);
                        System.out.println("Function set for connector");
                        Neighbors.set(i, cn);
                        cn.start();
                        System.out.println("Thread running.");
                        Thread.sleep(15000);
                    }
                }    
            }
        }
    }
}