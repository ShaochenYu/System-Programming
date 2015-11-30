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
			
			KeyValueServerInterface obj = new KeyValueServerInterfaceImpl();
			Registry registry = LocateRegistry.getRegistry(ip);
			registry.rebind("KeyValueServerInterfaceImpl",obj);
			
			//System.out.println("KeyValue server" + obj + "registered");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}