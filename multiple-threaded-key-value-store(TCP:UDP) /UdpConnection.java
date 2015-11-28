import java.util.*; 
import java.net.*;
import java.io.*;
//import java.io.File;

// Independent thread to handle a connection
class  UdpConnection implements Runnable
{	
	DatagramSocket serverSocket;
	DatagramPacket request;
	LogHelper loghelper;
	HashMap<String, String> hashmap;
	
	public UdpConnection(DatagramPacket request,DatagramSocket serverSocket, LogHelper loghelper, HashMap<String, String> hashmap) throws Exception{
		this.serverSocket = serverSocket;
		this.request = request;
		this.loghelper = loghelper;
		this.hashmap = hashmap;
		this.run();
	}
	
	public void run(){
		
		String ip = request.getAddress().getHostAddress();

		String data = new String(request.getData(), 0, request.getLength());
		
		// shared resource: hashmap should be synchronized
		// reponse to PUT/GET/DEL request
		String out_data = UtilTool.requestHandler(data,hashmap,ip,request.getPort(),loghelper);
		
		if( out_data != null ){
		
			byte[] rep_buf = out_data.getBytes();
			
			DatagramPacket reply = new DatagramPacket(rep_buf, rep_buf.length, request.getAddress(),request.getPort());
			// send reply
			// sychronized method
			try{
				synchronized(this){
					serverSocket.send(reply);
				}
			}catch(IOException e){
				System.out.println("IO Exception: " + e.getMessage());
			}
		}
	
	}
	
}

