package src.manager;

import java.rmi.Remote;

public interface RMILightBulb extends Remote
{
	public void on() throws java.rmi.RemoteException;
	public void off() throws java.rmi.RemoteException;
	public boolean isOn() throws java.rmi.RemoteException;
}
