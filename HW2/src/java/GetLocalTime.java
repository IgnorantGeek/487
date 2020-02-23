package java;

public class GetLocalTime
{
    c_int time;
    c_char valid;

    public GetLocalTime() { }

    public void execute(String IP, int Port)
    {
        // execute the command on the target machine
        int length = time.getSize() + valid.getSize();
        byte[] buffer = new byte[104+length];
        String id = "GetLocalTime";
        for (int i = 0; i < 100; i++)
        {
            if (i < id.length())
            {
                buffer[i] = (byte) id.charAt(i);
            }
            else buffer[i] = (byte) '\0';
        }
        byte[] size = Defaults.toBytes(length);
        for (int i = 0; i < 4; i++)
        {
            buffer[100+i] = size[i];
        }
    }
}