package client;

public class c_char
{
    byte buf;

    public c_char() { }

    public c_char(byte b)
    {
        this.buf = b;
    }

    public c_char(char c)
    {
        this.buf = (byte) c;
    }

    public byte getByte()
    {
        return this.buf;
    }

    public char getValue()
    {
        return (char) this.buf;
    }

    public int getSize()
    {
        return 1; // Size should always be 1, only 1 byte
    }

    public void setValue(byte b)
    {
        this.buf = b;
    }

    public void setValue(char c)
    {
        this.buf = (byte) c;
    }
}