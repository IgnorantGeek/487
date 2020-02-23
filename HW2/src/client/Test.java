package client;

public class Test
{
    public static void main(String[] args)
    {
        GetLocalTime time = new GetLocalTime();
        //GetLocalOS os = new GetLocalOS();

        time.execute("127.0.0.1", 1069);
    }
}