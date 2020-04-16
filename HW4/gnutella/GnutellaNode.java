package gnutella;

import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public int[] IP = new int[4];
    public int Port;
    public ArrayList<GnutellaNode> Neighbors = new ArrayList<GnutellaNode>();

    public GnutellaNode() { }

}