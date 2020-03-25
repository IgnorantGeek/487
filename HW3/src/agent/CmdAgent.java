package src.agent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CmdAgent extends Remote
{
    public Object execute(String CmdID, Object CmdObj) throws RemoteException;
}