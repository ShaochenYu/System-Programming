import java.rmi.registry.*;
import java.rmi.*;
import java.net.InetAddress;

// start server, register remote object

public class RegisterWithKeyValueServer
{	
	public static void main(String[] args){
		try{
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			System.setProperty("java.rmi.server.hostname", ip );
			
			KeyValueServerInterface obj1 = new KeyValueServerInterfaceImpl("server1");
			
			KeyValueServerInterface obj2 = new KeyValueServerInterfaceImpl("server2");
			KeyValueServerInterface obj3 = new KeyValueServerInterfaceImpl("server3");
			KeyValueServerInterface obj4 = new KeyValueServerInterfaceImpl("server4");
			KeyValueServerInterface obj5 = new KeyValueServerInterfaceImpl("server5");
			
			Registry registry = LocateRegistry.getRegistry(ip);
			
			registry.rebind("server1",obj1);
			
			registry.rebind("server2",obj2);
			registry.rebind("server3",obj3);
			registry.rebind("server4",obj4);
			registry.rebind("server5",obj5);
			
			}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}