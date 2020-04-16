package java;

public class Header
{
    String ID;
    int pl_descriptor;
    int TTL;
    int Hops;
    int pl_length;

    public Header() { }

    public Header(String ID, int pl_descriptor, int TTL, int Hops, int pl_length)
    {
        this.ID = ID;
        this.pl_descriptor = pl_descriptor;
        this.TTL = TTL;
        this.Hops = Hops;
        this.pl_length = pl_length;
    }

    public byte[] serialize()
    {
        byte[] serial = new byte[23];
        byte[] ID_byte = ID.getBytes();
        byte[] pl_length_byte = Macro.toBytes(pl_length);

        for (int i = 0; i < ID_byte.length; i++)
        {
            serial[i] = ID_byte[i];
        }
        
        serial[16] = (byte) pl_descriptor;
        serial[17] = (byte) TTL;
        serial[18] = (byte) Hops;

        for (int i = 0; i < pl_length_byte.length; i++)
        {
            serial[i+18] = pl_length_byte[i];
        }

        return serial;
    }

    public static Header deserialize(byte[] data)
    {
        Header in = new Header();



        return in;
    }
}