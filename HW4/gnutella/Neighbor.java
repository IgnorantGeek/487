package gnutella;

import java.util.ArrayList;

public class Neighbor
{
    public String ID;
    public String IP;
    public int Port;
    public ArrayList<Neighbor> Neighbors = new ArrayList<Neighbor>();
    public int NumFiles;
    public int friendCount;
    public long lastContact = -1;
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
        neighbor.Port = ((buf[0] & 0xff) << 8) | (buf[1] & 0xff);

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

        buf = new byte[1];

        buf[0] = payload[26];
        neighbor.friendCount = (buf[0] & 0xff);

        int connectFlag = payload[27] & 0xff;
        neighbor.connectFlag = connectFlag;

        // need to get the ips of the neighbors
        for (int i = 0; i < neighbor.friendCount; i++)
        {
            Neighbor friend = new Neighbor();
            byte[] port_buf = new byte[2];

            // Port read section
            port_buf[0] = payload[28+7*i];
            port_buf[1] = payload[29+7*i];
            friend.Port = ((port_buf[0] & 0xff) << 8) | (port_buf[1] & 0xff);

            // ip read section
            int friend_ip1 = payload[30+7*i] & 0xff;
            int friend_ip2 = payload[31+7*i] & 0xff;
            int friend_ip3 = payload[32+7*i] & 0xff;
            int friend_ip4 = payload[33+7*i] & 0xff;
            friend.IP = friend_ip1 + "." + friend_ip2 + "." + friend_ip3 + "." + friend_ip4;

            friend.friendCount = payload[34+7*i] & 0xff;
            neighbor.Neighbors.add(friend);
        }
        
        neighbor.lastContact = System.currentTimeMillis();
        return neighbor;
    }

    public static Neighbor decodeQuery(byte[] data)
    {
        Neighbor neighbor = new Neighbor();



        return neighbor;
    }
}