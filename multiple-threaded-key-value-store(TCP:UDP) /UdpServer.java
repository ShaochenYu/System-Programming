import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class UdpServer{
	
	static LogHelper loghelper = new LogHelper("UdpServerLogger");
	
	public static void main(String[] args) {
		
		DatagramSocket serverSocket = null;
		// use hashmap to store key-value pairs
		HashMap<String, String> hashmap = new HashMap<String, String>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		
		try{
			int port_num = new Integer(args[0]);
			serverSocket = new DatagramSocket(port_num);
			byte[] req_buf = new byte[1024];
			
			while(true){
				
				DatagramPacket request = new DatagramPacket(req_buf, req_buf.length);
				// receive request
				serverSocket.receive(request);
				
				// USE thread pool to handle every incoming request
				
				executor.execute( new UdpConnection( request, serverSocket, loghelper, hashmap) );

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
			
		} catch ( Exception e) {
				// EOFException
				loghelper.record("	[ERROR]	Ohter Exception!");
				System.out.println("Other Exception!" + e.getMessage());

			}finally {
			if( serverSocket != null )	serverSocket.close();
			
		}
		
		return ;
		
	}
	
}