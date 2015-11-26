import java.io.*;
import java.net.*;
import java.util.*;

class  TcpClient
{
	
	static LogHelper loghelper = new LogHelper("tcpClientLogger");
	
	
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
	
	public static void requestHandler ( String out_data, String ip, int port_num) {
		
		Socket clientSocket = null;
		
		try{
		
		clientSocket = new Socket(ip,port_num);
		// set socket timeout for 3 seconds
		clientSocket.setSoTimeout(3 * 1000);
	
		DataInputStream in = new DataInputStream( clientSocket.getInputStream() );
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream() );
		// send out request to server				
		out.writeUTF(out_data);
		
		String in_data = new String();
		
		// get request type GET/PUT/DEL
		String operation = out_data.substring(0,Math.min(out_data.length(),3));
		
		if( out_data.length() >= 3 && operation.equals("GET") ){
				
				in_data = in.readUTF();
				// return empty String if key is invalid
				if( in_data.length() == 0 )	
					loghelper.record("	[WARNING]	Invalid Key For GET Operation!" );
				else
					loghelper.record("	[MESSAGE]	Receive Data For GET Operation:	" + in_data);
		}
		
		} catch (SocketTimeoutException e){
			//timeout exception
				System.out.println("Socket Timeout Exception: " + e.getMessage() ); 
				loghelper.record("	[ERROR]	Socket Timeout Exception");
			
		} catch ( UnknownHostException e ){
				// log
				System.out.println("UnknownHostException: " + e.getMessage() );
				loghelper.record("	[ERROR]	UnknownHost Exception");
				
		} catch ( EOFException e ){

				System.out.println("EOF Exception: " + e.getMessage());

		} catch ( IOException e ){
				
				System.out.println("IO Exception: " + e.getMessage());
				loghelper.record("	[ERROR]	IO Exception");
				
		}catch ( ArrayIndexOutOfBoundsException e) {
				// basic validation for IP/Port Number
				System.out.println("Invalid Input IP/Port Number!");
				loghelper.record("	[ERROR]	Invalid Input IP/Port Number!");

		} finally {
				if( clientSocket != null )
					try {	
							clientSocket.close();
					} catch (IOException e){
						
							System.out.println("socket close failed");
							loghelper.record("	[ERROR]	Socket Close Failed");
							
					}
		}
		
		return ;
	}
	
	
}
