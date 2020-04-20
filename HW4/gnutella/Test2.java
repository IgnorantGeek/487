package gnutella;

public class Test2
{
    public static void main(String[] args) throws Exception
    {
        short port = 2077;
        GnutellaNode node = new GnutellaNode(port, Macro.DEFAULTPORT);
        
        node.Connect("127.0.0.1");
    }
}