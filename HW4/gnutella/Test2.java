package gnutella;

public class Test2
{
    public static void main(String[] args) throws Exception
    {
        int port = 2077;
        if (args.length == 1) port = Integer.parseInt(args[0]);
        GnutellaNode node = new GnutellaNode("127.0.0.1", port, Macro.DEFAULTPORT);
        
        node.Connect("127.0.0.1");
    }
}