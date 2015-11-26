import java.net.*;
import java.io.*;


public class UdpClient{
	
	// generate a UdpClientLogger.log in proj#1 folder
	static LogHelper loghelper = new LogHelper("UdpClientLogger");
	
	public static void main(String[] args) {
		
		int port_num = new Integer(args[1]);
		
		
		// use '/' as seperator
		
		// incomplete PUT request
		requestHandler("PUT/000", args[0], port_num);
		
		requestHandler("PUT/123/Andy",args[0],port_num);
		
		requestHandler("PUT/234/Lucy",args[0],port_num);
		
		requestHandler("PUT/345/Tom",args[0],port_num);
		
		requestHandler("PUT/456/Jack",args[0],port_num);
		
		requestHandler("PUT/567/Rose",args[0],port_num);
		
		
		// invalid GET request
		requestHandler("GET123", args[0], port_num);
		
		requestHandler("GET/123", args[0], port_num);
		
		requestHandler("GET/234", args[0], port_num);
		
		requestHandler("DEL/123", args[0], port_num);
		// insert <"123","Andrew">
		requestHandler("PUT/123/Andrew",args[0],port_num);
		// a valid GET operation
		requestHandler("GET/123", args[0], port_num);
		// valid GET operation
		requestHandler("GET/234", args[0], port_num);
		// invalid GET (non-exist key)
		requestHandler("GET/678", args[0], port_num);
		
		// invalid DEL request
		requestHandler("DEL/", args[0], port_num);
		
		requestHandler("DEL/234", args[0], port_num);
		// invalid DEL(non-exist)
		requestHandler("DEL/234", args[0], port_num);
		// insert <"234","Lily">
		requestHandler("PUT/234/Lily",args[0],port_num);
		// valid
		requestHandler("DEL/234", args[0], port_num);
		
		// other malformed requests
		requestHandler("GE", args[0], port_num);
		
		requestHandler("ADD/123/Tommy", args[0], port_num);
		
		return ;

	}
	
	public static void requestHandler(String data, String ip, int port_num){
		
		DatagramSocket clientSocket = null;
		
		try{
			clientSocket = new DatagramSocket();
			// set socket timeout as 3 seconds
			clientSocket.setSoTimeout(3 * 1000);
			
			byte[] req_buf = data.getBytes();
			
			DatagramPacket request = new DatagramPacket(req_buf, req_buf.length, InetAddress.getByName(ip), port_num );
			// send request
			clientSocket.send(request);
			// get request type GET/PUT/DEL
			String operation = data.substring(0,Math.min(data.length(),3));			
			// wait for reply if request is GET
			if( data.length() >= 3 && operation.equals("GET") ){
					
					byte[] rep_buf = new byte[1024];
					DatagramPacket reply = new DatagramPacket(rep_buf, rep_buf.length);
					
					clientSocket.receive(reply);
					
					String in_data = new String( reply.getData() );
					// return empty String if key is invalid
					if( reply.getLength() == 0 )
						loghelper.record("	[WARNING]	Invalid Key For GET Operation!" );
					else
						loghelper.record("	[MESSAGE]	Receive Data For GET Operation:	" + in_data);
			}
			
		} catch (SocketTimeoutException e){
			//timeout exception
			System.out.println("Socket Timeout Exception: " + e.getMessage() ); 
			loghelper.record("	[ERROR]	Socket Timeout Exception");
		
		} catch ( SocketException e ){

			System.out.println("Socket Exception: " + e.getMessage() );
			loghelper.record("	[ERROR]	Socket Exception");
			
		} catch ( IOException e ){
			
			System.out.println("IO Exception: " + e.getMessage());
			loghelper.record("	[ERROR]	IO Exception");
			
		} catch ( ArrayIndexOutOfBoundsException e) {
			// basic validation for IP/Port Number
			System.out.println("Invalid Input IP/Port Number!");
			loghelper.record("	[ERROR]	Invalid Input IP/Port Number!");
			
		} finally {
			if( clientSocket != null )	clientSocket.close();
			
		}
		
		return ;
	}
	
}