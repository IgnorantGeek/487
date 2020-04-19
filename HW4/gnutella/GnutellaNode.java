package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int ListenPort;
    public int ConnectPort;
    public ArrayList<GnutellaNode> Neighbors = new ArrayList<GnutellaNode>();

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
        this.ConnectPort = Port;
        ListenPort = Macro.DEFAULTPORT;
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
        // If we haven't made any connections, there is no pre-existing network
        connector = new Connector(address, ConnectPort, ID, Neighbors);
        connector.run();

        System.out.println("Connector running.....");
        Listener listener = new Listener(ListenPort, ID, Neighbors);
        listener.run();
    }

    public void Start() throws Exception
    {
        Listener listener = new Listener(ListenPort, ID, Neighbors);
        listener.run();
    }
}