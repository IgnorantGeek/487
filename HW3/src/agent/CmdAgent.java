package src.agent;

import java.rmi.Remote;

public interface CmdAgent extends Remote
{
    public Object execute(String CmdID, Object CmdObj);
}