package client;

import java.nio.ByteBuffer;

public class c_int
{
    byte[] buf = new byte[4];

    public c_int() { };

    public c_int(byte[] b)
    {
        this.buf = b;
    }

    public c_int(int i)
    {
        this.setValue(i);
    }

    public int getSize()
    {
        return 4; // I think the buffer size is always 4
    }

    public int getValue()
    {
        return ByteBuffer.wrap(this.buf).getInt();
    }

    public byte[] getByte()
    {
        return this.buf;
    }

    public void setValue(byte[] b)
    {
        this.buf = b;
    }

    public void setValue(int i)
    {
        this.buf = Defaults.toBytes(i);
    }
}