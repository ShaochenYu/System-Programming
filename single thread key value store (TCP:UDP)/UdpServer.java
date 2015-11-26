import java.util.*;
import java.net.*;
import java.io.*;


public class UdpServer{
	
	static LogHelper loghelper = new LogHelper("UdpServerLogger");
	
	public static void main(String[] args) {
		
		DatagramSocket serverSocket = null;
		// use hashmap to store key-value pairs
		HashMap<String, String> hashmap = new HashMap<String, String>();
		
		try{
			int port_num = new Integer(args[0]);
			serverSocket = new DatagramSocket(port_num);
			byte[] req_buf = new byte[1024];
			
			while(true){
				
				DatagramPacket request = new DatagramPacket(req_buf, req_buf.length);
				// receive request
				serverSocket.receive(request);
				
				String ip = request.getAddress().getHostAddress();

				String data = new String(request.getData(), 0, request.getLength());
				
				// reponse to PUT/GET/DEL request
				String out_data = UtilTool.requestHandler(data,hashmap,ip,request.getPort(),loghelper);
				
				if( out_data != null ){
				
					byte[] rep_buf = out_data.getBytes();
				
					DatagramPacket reply = new DatagramPacket(rep_buf, rep_buf.length, request.getAddress(),request.getPort());
					// send reply
					serverSocket.send(reply);
				}
			}
			
		} catch (SocketTimeoutException e){
			//timeout log
			loghelper.record("	[ERROR]	Socket Timeout Exception");
			System.out.println("Socket Timeout Exception: " + e.getMessage() ); 
		
		} catch ( SocketException e ){
			// log
			loghelper.record("	[ERROR]	Socket Exception");
			System.out.println("Socket Exception: " + e.getMessage() );
			
		} catch ( IOException e ){
			loghelper.record("	[ERROR]	IO Exception");
			System.out.println("IO Exception: " + e.getMessage());
		
		} catch ( ArrayIndexOutOfBoundsException e) {
			// log
			// basic validation for args[0] and args[1]
			loghelper.record("	[ERROR]	Invalid Input IP/Port Number!");
			System.out.println("Invalid Input Port Number!");
			
		} finally {
			if( serverSocket != null )	serverSocket.close();
			
		}
		
		return ;
		
	}
	
}