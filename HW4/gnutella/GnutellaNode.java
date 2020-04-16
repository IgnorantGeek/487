package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<GnutellaNode> Neighbors = new ArrayList<GnutellaNode>();

    public GnutellaNode() 
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        Port = 57176;
    }
}