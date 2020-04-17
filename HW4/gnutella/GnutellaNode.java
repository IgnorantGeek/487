package gnutella;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GnutellaNode
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<GnutellaNode> Neighbors = new ArrayList<GnutellaNode>();

    // Network variables
    Socket client        = null;
    ServerSocket server  = null;
    DataInputStream in   = null;
    DataOutputStream out = null;

    public GnutellaNode() throws Exception
    {
        ID = Macro.generateString(16);
        IP = Macro.getCurrentIp().getHostAddress();
        Port = Macro.DEFAULTPORT;
    }
}