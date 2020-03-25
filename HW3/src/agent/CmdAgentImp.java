package src.agent;

import src.misc.*;

public class CmdAgentImp implements CmdAgent
{
    public Object execute(String CmdID, Object CmdObj)
    {
        if (CmdID.equals("GetLocalTime")) 
        {
            System.out.println("Get Local Time RMI call.");
            return C_GetLocalTime((GetLocalTime) CmdObj);
        }
        else if (CmdID.equals("GetLocalOS"))
        {
            System.out.println("Get Local OS RMI call.");
            return c_GetLocalOS((GetLocalOS) CmdObj);
        }
        else return null;
    }

    private GetLocalTime C_GetLocalTime(GetLocalTime obj)
    {
        obj.setTime((int) System.currentTimeMillis());
        obj.setValid((char) 1);

        return obj;
    }

    private GetLocalOS c_GetLocalOS(GetLocalOS obj)
    {
        char[] OS = new char[16];
        OS[0] = 'l';
        OS[1] = 'i';
        OS[2] = 'n';
        OS[3] = 'u';
        OS[4] = 'x';
        obj.setOS(OS);
        obj.setValid((char) 1);

        return obj;
    }
}