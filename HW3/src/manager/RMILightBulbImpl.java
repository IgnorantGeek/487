package src.manager;

public class RMILightBulbImpl implements RMILightBulb
{	
	private boolean lightOn;
	
	public RMILightBulbImpl() throws java.rmi.RemoteException
	{
		setBulb(false);
	}
	
	public void on() throws java.rmi.RemoteException
	{
		setBulb(true);
		System.out.println("Bulb set to true.");
	}
	
	public void off() throws java.rmi.RemoteException
	{
		setBulb(false);
		System.out.println("Bulb set to false.");
	}
	
	public boolean isOn() throws java.rmi.RemoteException
	{
		return getBulb();
	}
	
	public void setBulb(boolean value)
	{
		lightOn = value;
	}
	
	public boolean getBulb()
	{
		return lightOn;
	}
}
