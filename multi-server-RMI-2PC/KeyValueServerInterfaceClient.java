import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.net.InetAddress;
import java.util.*;

public class KeyValueServerInterfaceClient
{	
	static LogHelper loghelper = new LogHelper("RMIClientLogger");
	private static KeyValueServerInterface server1, server2, server3, server4, server5;
	private static String local_ip;
		
	public static void initializeRMI(String ip){
		
		
		try {
			local_ip = InetAddress.getLocalHost().getHostAddress();			
			//lookup server
			server1 = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/server1");

			server2 = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/server2");

			server3 = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/server3");

			server4 = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/server4");

			server5 = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/server5");
			
			local_ip = InetAddress.getLocalHost().getHostAddress();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		assert( args[0]!= null && args[0] != "");
		try{
			
		/*
					test case:
					update at different servers, get at different servers , data consistence
		*/
		initializeRMI(args[0]);
		
		
		// incomplete PUT request
		doRequest("PUT/000", server1);
		
		// PUT request on different servers
		doRequest("PUT/123/Andy", server1);
		
		doRequest("PUT/234/Lucy", server2);
		
		doRequest("PUT/345/Tom", server3);
		
		doRequest("PUT/456/Jack", server4);
		
		doRequest("PUT/567/Rose", server5);
		
			
		// invalid GET request
		doRequest("GET123", server5);
		
		// GET request on different servers
		doRequest("GET/123", server5);
		
		doRequest("GET/234", server4);
		
		doRequest("GET/345", server3);
		
		doRequest("GET/456", server2);
		
		doRequest("GET/567", server1);
		
		// invalid DEL request
		doRequest("DEL/", server1);
		
		// DEL request on different servers
		
		doRequest("DEL/123", server1);
		
		doRequest("DEL/234", server2);
		
		// GET request on different servers
		
		doRequest("GET/123", server3);
		
		doRequest("GET/123", server4);
		
		// re-insert 
		
		doRequest("PUT/123/Andy", server1);
		
		// GET request
		
		doRequest("GET/123", server2);

		// other malformed requests
		doRequest("GE", server1);
		
		doRequest("ADD/123/Tommy", server2);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return ;		
	}
	
	public static void doRequest(String in_data, KeyValueServerInterface server) throws RemoteException{
		
		
		String type = UtilTool.checkType(in_data);
		
		
		if( type == null ){	
				loghelper.record("	[WARNING]	Invalid Operation!" );
				return ;
		}
			
		if( type.equals("GET") ){
			
				String res = server.read(in_data, local_ip);
				if( res.length() == 0 )	res = "NULL";
				
				loghelper.record("	[MESSAGE]	Receive Data For GET Operation:	" + res + " : from:" + server.getServerName() );
		
		}else{	
				// log prepare and commit info into log
				if( type.equals("DEL") || type.equals("PUT")  ){
					//System.out.println( server.getServerName() );
					server.write(in_data, local_ip);
				}else{
					loghelper.record("	[WARNING]	Invalid Operation! : " + in_data );
				}
		}
		
		return ;
	}

}