package gnutella;

public class Test
{
    public static void main(String[] args)
    {
        Header h1 = new Header("123456789abcdef", Macro.PING, 255, 0, 400);
        
        byte[] serial_h1 = h1.serialize();

        Header h2 = Header.deserialize(serial_h1);

        System.out.println(h2.ID);
        System.out.println(h2.TTL);
    }
}