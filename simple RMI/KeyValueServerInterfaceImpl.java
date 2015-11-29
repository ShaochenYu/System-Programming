import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class KeyValueServerInterfaceImpl extends UnicastRemoteObject implements KeyValueServerInterface{
		
	private HashMap<String, String> hashmap = new HashMap<String, String>();
	
	static LogHelper loghelper = new LogHelper("RMIServerLogger");
	
	public KeyValueServerInterfaceImpl() throws RemoteException{};
	
	public String requestHandler(String in_data, String ip) throws RemoteException{
		
		return UtilTool.requestHandler(in_data, hashmap, ip, 1099, loghelper);
		
	}
}