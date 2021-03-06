package src.misc;

import java.io.Serializable;
import java.util.Random;

public class Beacon implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int ID;
    private int StartupTime;
    private String CmdAgentID;

    public Beacon()
    {
        Random rand = new Random();
        this.ID = rand.nextInt(1000000);
        this.StartupTime = (int) System.currentTimeMillis();
        this.CmdAgentID = "CmdAgent" + ID;
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