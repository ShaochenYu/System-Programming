import java.rmi.*;
import java.util.*;


public interface  KeyValueServerInterface extends Remote
{	
	public ArrayList<KeyValueServerInterface> getServersList() throws RemoteException;
	public String getServerName() throws RemoteException;
	public void writeLog(String log_info) throws RemoteException;
}

