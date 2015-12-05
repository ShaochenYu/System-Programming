import java.rmi.*;
import java.util.*;


public interface  KeyValueServerInterface extends Remote
{	
	public String requestHandler(String in_data, String ip) throws RemoteException;
	public String read(String in_data, String ip) throws RemoteException;
	
}

