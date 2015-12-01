import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.net.InetAddress;

public class KeyValueServerInterfaceClient
{	
	static LogHelper loghelper = new LogHelper("RMIClientLogger");
	private static KeyValueServerInterface server;
	private static String local_ip;
		
	public static void initializeRMI(String ip){
		
		
		try {
			local_ip = InetAddress.getLocalHost().getHostAddress();			
			//lookup server
			server = (KeyValueServerInterface) Naming.lookup("//"+ip+":1099/KeyValueServerInterfaceImpl");
			System.out.println("KeyValue Server obj" + server + " found");
			local_ip = InetAddress.getLocalHost().getHostAddress();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public static void main(String[] args){
		assert( args[0]!= null && args[0] != "");
		try{
		initializeRMI(args[0]);
		
		
		// incomplete PUT request
		doRequest("PUT/000");
		
		doRequest("PUT/123/Andy");
		
		doRequest("PUT/234/Lucy");
		
		doRequest("PUT/345/Tom");
		
		doRequest("PUT/456/Jack");
		
		doRequest("PUT/567/Rose");
		
		
		// invalid GET request
		doRequest("GET123");
		
		doRequest("GET/123");
		
		doRequest("GET/234");
		
		doRequest("DEL/123");
		// insert <"123","Andrew">
		doRequest("PUT/123/Andrew");
		// a valid GET operation
		doRequest("GET/123");
		// valid GET operation
		doRequest("GET/234");
		// invalid GET (non-exist key)
		doRequest("GET/678");
		
		// invalid DEL request
		doRequest("DEL/");
		
		doRequest("DEL/234");
		// invalid DEL(non-exist)
		doRequest("DEL/234");
		// insert <"234","Lily">
		doRequest("PUT/234/Lily");
		// valid
		doRequest("DEL/234");
		
		// other malformed requests
		doRequest("GE");
		
		doRequest("ADD/123/Tommy");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return ;		
	}
	
	public static void doRequest(String in_data) throws RemoteException{
		String res = server.requestHandler(in_data,local_ip);
		
		String operation = in_data.substring(0,Math.min(in_data.length(),3));
		
		if( in_data.length() >= 3 && operation.equals("GET") ){
		
			if( res.length() == 0 )	
				loghelper.record("	[WARNING]	Invalid Key For GET Operation!" );
			else
				loghelper.record("	[MESSAGE]	Receive Data For GET Operation:	" + res);
	
		}
	}

}