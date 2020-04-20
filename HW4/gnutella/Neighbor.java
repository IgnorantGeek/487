package gnutella;

import java.util.ArrayList;

public class Neighbor
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<Neighbor> Neighbors = new ArrayList<Neighbor>();
    public int NumFiles;
    public int SharedSize;
    public int friendCount;
    public long lastContact;

    public Neighbor() { }

    public Neighbor(String ID)
    {
        this.ID = ID;
    }
}