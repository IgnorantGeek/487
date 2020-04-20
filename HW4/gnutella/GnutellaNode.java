package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public short ListenPort;
    public short ConnectPort;
    public ArrayList<Pair<Neighbor, Thread>> Neighbors = new ArrayList<Pair<Neighbor, Thread>>();

    // Network variables
    Thread connector = null;
    Thread listener   = null;

    public GnutellaNode()
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        ListenPort = Macro.DEFAULTPORT;
    }

    public GnutellaNode(short Port)
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        this.ListenPort = Port;
    }

    public GnutellaNode(short listen, short connect)
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
        // If we haven't made any connections, there is no pre-existing network
        connector = new Connector(address, ConnectPort, ID, Neighbors, Macro.OUTGOING);
        connector.start();

        Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
        listener.start();
    }

    public void Start() throws Exception
    {
        System.out.println("Creating network with root ID: " + ID);
        Listener listener = new Listener(ListenPort, ID, IP, Neighbors);
        listener.run();
    }
}