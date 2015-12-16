import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.rmi.registry.*;
import java.io.*;

public class KeyValueServerInterfaceImpl extends UnicastRemoteObject implements KeyValueServerInterface{
	
	private String server_name;
	
	private HashMap<String, String> hashmap = new HashMap<String, String>();
	
	//LogHelper loghelper;
	
	private boolean work;
	
	//**** Acceptor Atrr && Acceptor methods *****//
	HashMap<Integer, Instance> instances = new HashMap<Integer, Instance>();
	public int INS_NUM;
	
	public HashMap<String, String> getMap() throws RemoteException {
		
		return hashmap;
	}
	
	public HashMap<Integer, Instance> getInstances() throws RemoteException{
		
		return instances;
	}
	
	public int getNumOfIns()throws RemoteException{
		
		return INS_NUM;
	}
	
	public Instance prepare( int ins_num, int income_seq ) throws RemoteException {
		if( !work )	return null;
		
		Instance res = instances.get(ins_num);
		
		if( res == null )	return null;
		else{
			
			if( income_seq > res.n_p ){
				
				res.n_p = income_seq;
				
				return res;
			}else
				return null;
			
		}
		
	}
	
	public int accept(int ins_num, int income_seq, String content ) throws RemoteException {
		if( !work )	return -1;
		
		
		Instance res = instances.get(ins_num);
		
		if( res == null )	return -1;
		else{
			
			if( income_seq >= res.n_p ){
				
				res.n_p = income_seq;
				
				res.n_a = income_seq;
				
				res.content = content;
				
				res.decided = true;
				
				INS_NUM++;
				
				return income_seq;
			}
		}
		
		return -1;
		
	}
	
	//********** Proposer method ************//
	
	public int propose( int seq, Operation op) throws RemoteException {
		
		if( !work )	return seq;
		
		boolean decided = false;
		
		while( !decided ){
			
			seq++;
			
			ArrayList<KeyValueServerInterface> list = getServersList();
			
			int count = 0;
			
			int max = -1;
			
			
			for( KeyValueServerInterface server : list ){
				
				Instance ins = server.prepare(op.op_num , seq);
				
				if( ins != null ){
					count++;
					if( ins.n_a > max ){
						
						max = ins.n_a;
						//content = ins.content;
						op.content = ins.content;
					}
					
				}
				
			}
			if( count > (list.size()/2) ){
				
				if( sendAllAccept(op.op_num, seq, op.content, list) ){
					
					for( KeyValueServerInterface server : list ){
						
						server.decide(seq, op );
					}
				
				decided = true;	
				}
			}
		
		}
		
		return seq;
	}
	
	public boolean sendAllAccept(int ins_num, int income_seq, String content, ArrayList<KeyValueServerInterface> list ) throws RemoteException{
		
		int count = 0;
		
		for( KeyValueServerInterface server : list ){
			
			if( server.accept(ins_num, income_seq, content) != -1)	count++;
			
		}
		
		if( count > (list.size()/2) )	return true;
		
		return false;
	}
	
	//********** Learner method *************//
	
	public void decide(int income_seq ,Operation op) throws RemoteException {
		
		// set corresponding Op decided, assume no collision happens
		
		if( !work )	return ; 
		
		if( op == null )	return ;
		
		Instance ins = instances.get( op.op_num );
		
		if( !ins.decided ){
			
			ins.decided = true;
			
			INS_NUM++;
			
			ins.n_a = income_seq;
			
			ins.content = op.content;
			
		}
		
		return ;
		
	}
	
	
	public String getServerName() throws RemoteException{
		return server_name;
	}
	
	public KeyValueServerInterfaceImpl(String server_name, int instance_capibility) throws RemoteException{
		this.server_name = server_name;
		//loghelper = new LogHelper(server_name+"_log");
		INS_NUM = 0;
		
		work = true;
		
		for( int i = 1; i <= instance_capibility; i++ )
			instances.put(new Integer(i), new Instance() );
			
	}
	
	
	
	public void dump() throws RemoteException{
		int num_ins = instances.size();
		
		System.out.println(getServerName() + ":");
		
		for( int i = 1; i <= num_ins; i++ ){
			Instance tmp = instances.get(i);
			
			System.out.println(i + ": " + tmp.toString() );
			
		}
		
		return ;
		
	}
	
	public ArrayList<KeyValueServerInterface> getServersList() throws RemoteException{
		//
		ArrayList<KeyValueServerInterface> res = new ArrayList();
		
		try{
			Registry registry = LocateRegistry.getRegistry();
			String[] name = registry.list();
			for( String str : name ){
				
				//if( str.equals(server_name) ) continue;
				
				KeyValueServerInterface obj = (KeyValueServerInterface)registry.lookup(str);
				res.add(obj);
			}
		
		}catch(Exception e){
			System.out.println( e.getMessage() );
		}
		return res;
	}
	
	public void setWork(boolean work) throws RemoteException {
		
		this.work = work;
		
		return ;
	}
	
}