package gnutella;

import java.util.ArrayList;
import java.util.Random;

public class GnutellaNode
{
    // Local Variables
    public String ID;
    public String IP;
    public int ListenPort;
    public int ConnectPort;
    public ArrayList<Connector> Neighbors = new ArrayList<Connector>();
    private int interval;

    // Network variables
    Connector connector = null;
    Listener listener   = null;

    /*---------------Constructors---------------*/
    public GnutellaNode()
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        ListenPort = Macro.DEFAULTPORT;
        Random random = new Random();
        interval = random.nextInt(7);
    }

    public GnutellaNode(int Port)
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        this.ListenPort = Port;
        Random random = new Random();
        interval = random.nextInt(7);
    }

    public GnutellaNode(int listen, int connect)
    {
        ID = Macro.generateString(16);
        this.ListenPort = listen;
        this.ConnectPort = connect;
        Random random = new Random();
        interval = random.nextInt(7);
    }

    public GnutellaNode(String address)
    {
        ID = Macro.generateString(16);
        IP = address;
        this.ListenPort = Macro.DEFAULTPORT;
        Random random = new Random();
        interval = random.nextInt(7);
    }

    public GnutellaNode(String address, int listen, int connect)
    {
        ID = Macro.generateString(16);
        IP = address;
        this.ListenPort = listen;
        this.ConnectPort = connect;
        Random random = new Random();
        interval = random.nextInt(7);
    }

    /**
     * Connect to a pre-existing gnutella network
     * @param address the IP of the node to try to connect to
     */
    public void Connect(String address)
    {
        try
        {
            System.out.println("Interval: "  + interval);
            System.out.println("Joing network with ID: " + ID);
            System.out.println("Pinging " + IP + ":" + ConnectPort + " for network config....");
            Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
            listener.start();
            // try to join pre-existing network
            connector = new Connector(address, ConnectPort, ID, Neighbors, Macro.OUTGOING);
            connector.start();

            // Check neighbors for incoming messages, and whether or not to ping
            CheckNeighborLoop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create a new gnutella network with this as the root node
     */
    public void Start()
    {
        try
        {
            System.out.println("Interval: "  + interval);
            System.out.println("Creating network with root ID: " + ID);
            Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
            listener.start();

            // Iterate over the current neighbor and handle any send/recieve calls
            CheckNeighborLoop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // TODO: fix me
    public void CheckNeighborLoop() throws Exception
    {
        while (true)
        {
            // Check all verified neighbors for an incoming connection
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

            // Check all verified neighbors for un-initialized nodes
            for (int i = 0; i < Neighbors.size(); i++)
            {
                Connector c = Neighbors.get(i);
                
                // If a node doesn't have a valid IP, send a PING
                // Also if a node hasn't been heard from in 1 minute
                if (c.neighbor.IP == null
                ||  System.currentTimeMillis() - c.neighbor.lastContact > 60000)
                {
                    if (!c.isAlive())
                    {
                        Connector cn = new Connector(c);
                        cn.setFunction(Macro.PING);
                        Neighbors.set(i, cn);
                        cn.start();
                        Thread.sleep(10000);
                    }
                }   
            }

            // Check all neighbors and ping friends if requirements are met
            // if (Neighbors.size() < 6)
            // {
            //     outerloop:
            //     for (int i = 0; i < Neighbors.size(); i++)
            //     {
            //         Connector c = Neighbors.get(i);
            //         for (Neighbor n : c.neighbor.Neighbors)
            //         {
            //             // if this neighbor isn't already connected, try to contact
            //             System.out.println(n.Port);
            //             System.out.println(n.IP);
            //         }
            //     }
            // }

            // Wait interval seconds and run again
            Thread.sleep(interval * 1000);
        }
    }
}