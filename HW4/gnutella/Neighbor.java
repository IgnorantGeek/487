package gnutella;

import java.util.ArrayList;

public class Neighbor
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<Neighbor> Neighbors = new ArrayList<Neighbor>();
    public long lastContact; 

    public Neighbor() { }

    public Neighbor(String ID)
    {
        this.ID = ID;
    }
}