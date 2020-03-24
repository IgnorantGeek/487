package src.agent;

public class GetLocalOS
{
    char[] OS = new char[16];
    char valid;

    /**
     * @return the oS
     */
    public char[] getOS() {
        return OS;
    }

    /**
     * @return the valid
     */
    public char getValid() {
        return valid;
    }
    
    /**
     * @param oS the oS to set
     */
    public void setOS(char[] oS) {
        OS = oS;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(char valid) {
        this.valid = valid;
    }
}