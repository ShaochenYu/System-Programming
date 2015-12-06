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
	
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
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
			
	};
	
	
	
	public String read(String in_data, String ip) throws RemoteException{
		rwl.readLock().lock();
		String res = new String();
		
		try{
			res = requestHandler(in_data, ip);
		}catch(Exception e){
			System.out.println( e.getMessage() );
		}finally{
			rwl.readLock().unlock();
		}
	
		return res;
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
	
	public void writeUndoLogo(String in_data) throws RemoteException{
		
		StringBuffer res = new StringBuffer();
		
		String[] req = in_data.split("/");
		
		String type = UtilTool.checkType(in_data);
		
		if( type == null )	return ;
		
		if( type.equals("DEL") ){
			
			res.append("PUT/");
			res.append(req[1]);
			res.append("/");
			String value = hashmap.get(req[1]);
			res.append(value);
		}
		
		if( type.equals("PUT") ){
			
			res.append("DEL/");
			res.append(req[1]);
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File( server_name+"_undolog" ) ));
			
			bw.write(res.toString());
			
			bw.close();
		}catch( Exception e ){
			System.out.println( e.getMessage() );
		}
		
		return ;
	}
	
	public void undo() throws RemoteException{
		
		try{
			
			BufferedReader bf = new BufferedReader(new FileReader(new File(server_name+"_undolog")));
			String content = bf.readLine();
			
			bf.close();
			
		}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
		
		return ;
	}
	
	public boolean prepare(String in_data, ArrayList<KeyValueServerInterface> servers_list) throws RemoteException{
				
		// need to write prepare process into coordinators' log and subordinators' log 
		for( KeyValueServerInterface server : servers_list){
			
				server.writeLog("receive prepare info: " + in_data);
				server.writeUndoLogo(in_data);
				server.writeLog("write undo log");
			try{
				server.writeLog("lock resources");
				server.writeLog("reply yes");
				writeLog("receive yes from: " + server.getServerName() );
			}catch(Exception e){
				System.out.println( e.getMessage() );
			}
		}
		// there is no negative voting result in this project.
		
		//set condition for negative voting
			
		return true;
		
	}
	
	
	public void commit(boolean voting_result,ArrayList<KeyValueServerInterface> servers_list, String in_data, String ip) throws RemoteException{
		// need to write commit process into coordinators' log and subordinators' log
		
		if(!voting_result){
				for(KeyValueServerInterface server:servers_list){
						server.writeLog( "receive abort: " + in_data );
						server.undo();
						server.writeLog("unlock resources");
						server.writeLog("reply ack");
				}
		}else{
			
			// write server log here
			for(KeyValueServerInterface server:servers_list){
					if( !server.getServerName().equals(server_name) ){
						
						server.writeLog("receive commit: " + in_data );
						server.writeLog("unlock resources");
						server.writeLog("reply ack");
						writeLog( "receive ack from: " + server.getServerName() );
						server.writeLog("complete: ");
						server.requestHandler(in_data, ip);
						
					}
			}
		}
		
		return ;
	}
	
	public void write(String in_data, String ip)throws RemoteException{
		
		rwl.writeLock().lock();
		
		ArrayList<KeyValueServerInterface> servers_list = getServersList();
		writeLog("receive request as coordinator: ");
		writeLog("send prepare info to all cohorts: " + in_data);
					
		boolean voting_result = prepare( in_data, servers_list);
		
		String desicion = voting_result ? "commit" : "abort";
		
		writeLog("send " + desicion + " info to all cohorts: " + in_data);
		
		commit( voting_result ,servers_list, in_data, ip);
		
		writeLog("complete: ");
		
		requestHandler(in_data, ip);
		
		rwl.writeLock().unlock();
		
		return ;
	}
	
	public String requestHandler(String in_data, String ip) throws RemoteException{
		
		return UtilTool.requestHandler(in_data, hashmap, ip, 1099, loghelper);
		
	}
}