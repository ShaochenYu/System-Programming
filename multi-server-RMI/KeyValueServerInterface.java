import java.rmi.*;
import java.util.*;


public interface  KeyValueServerInterface extends Remote
{	
	public String requestHandler(String in_data, String ip) throws RemoteException;
	public String read(String in_data, String ip) throws RemoteException;
	public ArrayList<KeyValueServerInterface> getServersList() throws RemoteException;
	public void writeUndoLogo(String in_data) throws RemoteException;
	//public String checkType(String in_data) throws RemoteException;
	public void undo() throws RemoteException;
	public boolean prepare(String in_data, ArrayList<KeyValueServerInterface> servers_list) throws RemoteException;
	public void commit(boolean voting_result,ArrayList<KeyValueServerInterface> servers_list, String in_data, String ip) throws RemoteException;
	public void write(String in_data, String ip) throws RemoteException;
	public String getServerName() throws RemoteException;
	public void writeLog(String log_info) throws RemoteException;
}

