package gnutella;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        GnutellaNode node = new GnutellaNode(51717);

        node.Connect("192.168.1.116");
    }
}