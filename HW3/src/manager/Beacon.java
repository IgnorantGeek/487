package src.manager;

public class Beacon
{
    int ID;
    int StartupTime;
    int Interval;
    int CmdPort;
    int[] IP = new int[4];

    public Beacon() { /* */ }

    public Beacon(int ID, int StartupTime, int Interval, int CmdPort, int[] IP)
    {
        this.ID = ID;
        this.StartupTime = StartupTime;
        this.Interval = Interval;
        this.CmdPort = CmdPort;
        this.IP = IP;
    }

    public void print()
    {
        System.out.println("\n---------Beacon Info---------" +
                           "\nID:                  " + ID +
                           "\nClient Startup Time: " + StartupTime +
                           "\nInterval (min):      " + Interval +
                           "\nClient IP:           " + IP[0] + "." + IP[1] + "." + IP[2] + "." + IP[3] +
                           "\nCommand (TCP) Port:  " + CmdPort + '\n');
    }

    public boolean compareTo(Beacon b)
    {
        if (this.ID == b.ID && this.StartupTime == b.StartupTime) return true;
        else return false;
    }
}