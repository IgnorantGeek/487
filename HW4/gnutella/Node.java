package gnutella;

public class Node
{
    public static void main(String[] args)
    {
        int port = 2078;
        if (args.length == 1) port = Integer.parseInt(args[0]);
        GnutellaNode node = new GnutellaNode(port, 2076);
        
        node.Connect("127.0.0.1");
    }
}