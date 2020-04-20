package gnutella;

import java.util.ArrayList;

public class Neighbor
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<Neighbor> Neighbors = new ArrayList<Neighbor>();
    public int NumFiles;
    public int SharedSize;
    public int friendCount;
    public long lastContact;
    public int connectFlag;

    public Neighbor() { }

    public Neighbor(String ID)
    {
        this.ID = ID;
    }

    public static Neighbor decodePong(byte[] payload)
    {
        Neighbor neighbor = new Neighbor();
        byte[] buf = new byte[16];
        for (int i = 0; i < buf.length; i++)
        {
            buf[i] = payload[i];
        }

        neighbor.ID = new String(buf);

        buf = new byte[2];
        buf[0] = payload[16];
        buf[1] = payload[17];
        neighbor.Port = Macro.toInteger(buf);

        int ip1 = payload[18] & 0xff;
        int ip2 = payload[19] & 0xff;
        int ip3 = payload[20] & 0xff;
        int ip4 = payload[21] & 0xff;
        neighbor.IP = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

        buf = new byte[4];
        for (int i = 0; i < buf.length; i++)
        {
            buf[i] = payload[22+i];
        }
        neighbor.NumFiles = Macro.toInteger(buf);
        
        for (int i = 0; i < buf.length; i++)
        {
            buf[i] = payload[26+i];
        }
        neighbor.SharedSize = Macro.toInteger(buf);

        buf = new byte[1];

        buf[0] = payload[30];
        neighbor.friendCount = Macro.toInteger(buf);

        int connectFlag = payload[31] & 0xff;
        neighbor.connectFlag = connectFlag;

        // need to get the ips of the neighbors
        for (int i = 0; i < neighbor.friendCount; i++)
        {
            Neighbor friend = new Neighbor();
            byte[] port_buf = new byte[2];

            // Port read section
            port_buf[0] = payload[32+7*i];
            port_buf[1] = payload[33+7*i];
            friend.Port = Macro.toInteger(port_buf);

            // ip read section
            int friend_ip1 = payload[34+7*i] & 0xff;
            int friend_ip2 = payload[35+7*i] & 0xff;
            int friend_ip3 = payload[36+7*i] & 0xff;
            int friend_ip4 = payload[37+7*i] & 0xff;
            neighbor.IP = friend_ip1 + "." + friend_ip2 + "." + friend_ip3 + "." + friend_ip4;

            neighbor.friendCount = payload[38+7*i] & 0xff;
        }

        return neighbor;
    }

    public static Neighbor decodeQuery(byte[] data)
    {
        Neighbor neighbor = new Neighbor();

        return neighbor;
    }
}