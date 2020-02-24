package client;

public class Test
{
    public static void main(String[] args)
    {
        int port = 1069;
        String IP = "127.0.0.1";
        if (args.length == 0)
        {
            System.out.println("TCP Client - using defaults, localhost:1069");
        }
        else if (args.length == 1)
        {
            System.out.println("TCP Client - using localhost:" + args[0]);
            port = Integer.parseInt(args[0]);
        }
        else if (args.length == 2)
        {
            System.out.println("TCP Client - using " + args[0] + ":" + args[1]);
            port = Integer.parseInt(args[1]);
            IP = args[0];
        }
        else
        {
            System.out.println("Usage -- Test (default) || Test <port> || Test <hostname> <port>");
            return;
        }
        GetLocalTime time = new GetLocalTime();
        //GetLocalOS os = new GetLocalOS();

        //os.execute("127.0.0.1", 1069);
        time.execute(IP, port);
    }
}