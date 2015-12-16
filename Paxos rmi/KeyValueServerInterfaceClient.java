import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.net.InetAddress;
import java.util.*;

public class KeyValueServerInterfaceClient
{	
	private static KeyValueServerInterface server1, server2, server3, server4, server5;
	private static String local_ip;
	private static Integer OPERATION_NUM;
	private static int SQUENCE_NUM;
	
	
	public static void initializeRMI(){
		
		
		try {
			OPERATION_NUM = new Integer(0);
			SQUENCE_NUM = 0;
			
			local_ip = InetAddress.getLocalHost().getHostAddress();			
			//lookup server
			server1 = (KeyValueServerInterface) Naming.lookup("//"+local_ip+":1099/server1");

			server2 = (KeyValueServerInterface) Naming.lookup("//"+local_ip+":1099/server2");

			server3 = (KeyValueServerInterface) Naming.lookup("//"+local_ip+":1099/server3");

			server4 = (KeyValueServerInterface) Naming.lookup("//"+local_ip+":1099/server4");

			server5 = (KeyValueServerInterface) Naming.lookup("//"+local_ip+":1099/server5");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		
		try{
			
		/*
		test case:
		1 compete on the same operation( operation number is the same)
		2 server fails, if majority of servers fail, consensus will not be achieved, if minority of servers fail, consensus will be achieved.
		3 update at different servers, get at different servers , achieve data consistence
		*/
		initializeRMI();
		
		// server2 fail
		setFail(server2);
		
		put(server1, "123/andy");
		
		restart(server2);
		
		// compete the same Operation. operation number goes back.
		OPERATION_NUM = new Integer(OPERATION_NUM.intValue() - 1);
		
		put(server2, "123/andy");
		
		// get result, former operation is fullfilled 
		System.out.println("Get operation: " + get(server3, "123"));
		
		
		// server1 and server2 fail
		
		setFail(server1);
		setFail(server2);
		
		put(server3, "234/jack");
		
		restart(server1);
		restart(server2);
		
		// can achive consensus, majority of servers work
		System.out.println("Get operation: " + get(server3, "234"));
		
		// server2, server3, server5 fail 
		
		setFail(server2);
		setFail(server3);
		setFail(server5);
		
		put(server3, "345/rose");
		
		restart(server2);
		restart(server3);
		restart(server5);
		
		//can not achive consensus, majority of servers fail
		System.out.println("Get operation: " + get(server3, "345"));
		
		
		setFail(server2);
		// request will not be effective, server2 fails
		delete(server2, "123");
		
		restart(server2);
		
		// deletion operation fail, record stay there
		System.out.println("Get operation: " + get(server3, "123"));
		
		
		// server2, server3, server5 fail
		setFail(server2);
		setFail(server3);
		setFail(server5);
		
		delete(server3, "123");
		
		restart(server2);
		restart(server3);
		restart(server5);
		
		//deletion operation fail, record stay there
		System.out.println("Get operation: " + get(server3, "123"));
		
		
		// server3, server4 fail
		setFail(server3);
		setFail(server4);
		
		delete(server5, "123");
		
		restart(server3);
		restart(server4);
		
		//deletion operation success, record is removed
		System.out.println("Get operation: " + get(server3, "123"));
		
		put(server2, "456/rose");
		
		System.out.println("Get operation: " + get(server3, "456"));
		
		delete(server3, "456");
		
		System.out.println("Get operation: " + get(server4, "456"));
		
		allInfo();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return ;		
	}
	
	public static void put(KeyValueServerInterface server, String content) throws Exception {
		
		OPERATION_NUM = new Integer(OPERATION_NUM.intValue() + 1);
		
		Operation op = new Operation(OPERATION_NUM, "PUT/"+content);
		
		SQUENCE_NUM = server.propose(SQUENCE_NUM,op);
		
		//System.out.println(SQUENCE_NUM);		
	
		return ;
	}
	
	public static void delete(KeyValueServerInterface server, String content) throws Exception {
		
		OPERATION_NUM = new Integer(OPERATION_NUM.intValue() + 1);
		
		Operation op = new Operation(OPERATION_NUM, "DEL/"+content);
		
		SQUENCE_NUM = server.propose(SQUENCE_NUM,op);
	
		return ;
	}
	
	public static String get(KeyValueServerInterface server, String content) throws Exception {
		
		OPERATION_NUM = new Integer(OPERATION_NUM.intValue() + 1);
		
		Operation op = new Operation(OPERATION_NUM, "GET/"+content);
		
		SQUENCE_NUM = server.propose(SQUENCE_NUM,op);
		
		ArrayList<KeyValueServerInterface> list = server.getServersList();
		
		int sel = -1;
		KeyValueServerInterface target = server;
		
		for( KeyValueServerInterface s : list ){
			
			if( s.getNumOfIns() > sel  ){
				
				sel = s.getNumOfIns();
				target = s;
			}
		}
		
		HashMap<String, String> hm = new HashMap<String, String>(target.getMap());
		
		doOperations(hm,target.getInstances());
		
		String res = UtilTool.requestHandler(op.content,hm);
		
		if( res.length() == 0 )	return "null";
		
		return res;
	}
	
	public static void doOperations(HashMap<String, String> hm, HashMap<Integer, Instance> instances ){
		
		int size = instances.size();
		
		for( int i = 1; i <= size; i++ ){
			
			Instance ins = instances.get( i );
			
			if( ins.decided ){
			
				UtilTool.requestHandler(ins.content, hm);
				
			}
			
		}
		
		return ;
	}
	
	public static void setFail( KeyValueServerInterface server ) throws Exception{
		
		server.setWork(false);
		
		return ;
	}
	
	public static void restart(KeyValueServerInterface server) throws Exception{
		
		server.setWork(true);
		
		return ;
	}
	
	public static void allInfo() throws Exception{
		
		server1.dump();
		server2.dump();
		server3.dump();
		server4.dump();
		server5.dump();
	}


}