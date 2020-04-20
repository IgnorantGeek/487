package gnutella;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Macro
{
    public static int PING = 0x00;
    public static int PONG = 0x01;
    public static int PUSH = 0x40;
    public static int QUERY = 0x80;
    public static int QUERYHIT = 0x81;
    public static int INCOMING = 0x16;
    public static int OUTGOING = 0x17;
    public static short DEFAULTPORT = (short) 2076;


    public static int toInteger(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] to4Bytes(int x)
    {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(x).array();
    }

    public static String generateString(int n) 
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++)
        {
            int index = (int)(AlphaNumericString.length() * Math.random()); 
            sb.append(AlphaNumericString.charAt(index)); 
        }
        return sb.toString();
    } 



    public static InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() 
                     && !ia.isLoopbackAddress()
                     && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    public static byte[] IpTo4Bytes(String address)
    {
        byte[] ret = new byte[4];

        String hold = new String();
        String[] nums = new String[4];
        int count = 0;
        for (int i = 0; i < address.length(); i++)
        {
            if (address.charAt(i) == '.') 
            {
                nums[count] = hold;
                count++;
                hold = "";
            }
            else hold += address.charAt(i);
        }
        nums[3] = hold;

        for (int i = 0; i < 4; i ++)
        {
            Integer a = Integer.parseInt(nums[i]);
            ret[i] = a.byteValue();
        }

        return ret;
    }
}