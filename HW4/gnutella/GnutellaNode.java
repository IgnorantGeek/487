package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    // Local Variables
    public String ID;
    public String IP;
    public int ListenPort;
    public int ConnectPort;
    public ArrayList<Connector> Neighbors = new ArrayList<Connector>();

    // Network variables
    Connector connector = null;
    Listener listener   = null;

    /*---------------Constructors---------------*/
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

    /**
     * Connect to a pre-existing gnutella network
     * @param address the IP of the node to try to connect to
     */
    public void Connect(String address)
    {
        try
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
                        Connector cn = new Connector(c);
                        cn.setFunction(Macro.READ);
                        Neighbors.set(i, cn);
                        cn.start();
                    } 
                }

                // Wait 5 seconds and run again
                Thread.sleep(5000);
            }
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
            System.out.println("Creating network with root ID: " + ID);
            Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
            listener.start();

            // Iterate over the current neighbor and handle any send/recieve calls
            while (true)
            {
                // Check all verified neighbors for an incoming connection
                for (int i = 0; i < Neighbors.size(); i++)
                {
                    Connector c = Neighbors.get(i);
                    if (c.in.available() != 0)
                    {
                        // We have a connection. Need to read the header.
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
                    if (c.neighbor.IP == null)
                    {
                        System.out.println("PINGING");
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

                // Wait 5 seconds and run again
                Thread.sleep(5000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}