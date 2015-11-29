import java.rmi.*;

public interface  KeyValueServerInterface extends Remote
{	
	public String requestHandler(String in_data, String ip) throws RemoteException;

}

