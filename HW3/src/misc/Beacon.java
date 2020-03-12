package src.misc;

import java.util.Random;

public class Beacon
{
    private int ID;
    private int StartupTime;
    private String CmdAgentID;

    public Beacon()
    {
        Random rand = new Random();
        this.ID = rand.nextInt(1000000);
        this.StartupTime = (int) System.currentTimeMillis();
        this.CmdAgentID = new String();
    }

    public Beacon(String cmdString)
    {
        Random rand = new Random();
        this.ID = rand.nextInt(1000000);
        this.StartupTime = (int) System.currentTimeMillis();
        this.CmdAgentID = new String(cmdString);
    }

    /**
     * @return the cmdAgentID
     */
    public String getCmdAgentID()
    {
        return CmdAgentID;
    }

    /**
     * @return the iD
     */
    public int getID()
    {
        return ID;
    }

    /**
     * @return the startupTime
     */
    public int getStartupTime()
    {
        return StartupTime;
    }
}