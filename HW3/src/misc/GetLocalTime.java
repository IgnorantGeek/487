package src.misc;

import java.io.Serializable;

public class GetLocalTime implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int time;
    char valid;

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @return the valid
     */
    public char getValid() {
        return valid;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(char valid) {
        this.valid = valid;
    }
}