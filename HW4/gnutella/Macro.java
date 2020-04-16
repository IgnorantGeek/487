package gnutella;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Macro
{
    public static int PING = 0x00;
    public static int PONG = 0x01;
    public static int PUSH = 0x40;
    public static int QUERY = 0x80;
    public static int QUERYHIT = 0x81;

    public static int toInteger(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] toBytes(int n)
    {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(n).array();
    }
}