package gnutella;

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
        byte[] buf1 = new byte[16];
        for (int i = 0; i < 16; i++)
        {
            buf1[i] = data[i];
        }
        String id = new String(buf1);
        
        int pld = data[16] & 0xFF;
        int ttl = data[17] & 0xFF;
        int hop = data[18] & 0xFF;

        byte[] buf2 = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            buf2[i] = data[i + 18];
        }

        int pl_len = Macro.toInteger(buf2);

        return new Header(id, pld, ttl, hop, pl_len);
    }
}