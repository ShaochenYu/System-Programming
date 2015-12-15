import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.rmi.registry.*;
import java.io.*;

public class KeyValueServerInterfaceImpl extends UnicastRemoteObject implements KeyValueServerInterface{
	
	private String server_name;
	
	private HashMap<String, String> hashmap = new HashMap<String, String>();
	
	LogHelper loghelper;
	
	public String getServerName() throws RemoteException{
		return server_name;
	}
	
	public void writeLog(String log_info) throws RemoteException{
		this.loghelper.record(log_info);
		return ;
	}
	
	public KeyValueServerInterfaceImpl(String server_name) throws RemoteException{
		this.server_name = server_name;
		loghelper = new LogHelper(server_name+"_log");
			
	}
	
	public ArrayList<KeyValueServerInterface> getServersList() throws RemoteException{
		//
		ArrayList<KeyValueServerInterface> res = new ArrayList();
		
		try{
			Registry registry = LocateRegistry.getRegistry();
			String[] name = registry.list();
			for( String str : name ){
				
				if( str.equals(server_name) ) continue;
				
				KeyValueServerInterface obj = (KeyValueServerInterface)registry.lookup(str);
				res.add(obj);
			}
		
		}catch(Exception e){
			System.out.println( e.getMessage() );
		}
		return res;
	}
}