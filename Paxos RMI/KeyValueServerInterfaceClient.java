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
		
	public static void initializeRMI(){
		
		
		try {
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
			
		initializeRMI();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ;		
	}

}