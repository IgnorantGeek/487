package java;

import java.nio.ByteBuffer;

public class c_int
{
    byte[] buf = new byte[4];

    public c_int() { };

    public c_int(byte[] b)
    {
        this.buf = b;
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
        byte[] hold = new byte[4];
        hold[0] = (byte) (i >> 24);
        hold[1] = (byte) (i >> 16);
        hold[2] = (byte) (i >> 8);
        hold[3] = (byte) (i /*>> 0*/);
        this.buf = hold;
    }
}