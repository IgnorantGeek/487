package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<GnutellaNode> Neighbors = new ArrayList<GnutellaNode>();

    // Network variables
    Client client = null;
    Server server = null;

    public GnutellaNode()
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        Port = Macro.DEFAULTPORT;
    }

    public void Connect() throws Exception
    {
        server = new Server(Port);

        server.run();
    }
}