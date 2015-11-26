import java.net.*;
import java.io.*;
import java.util.*;

class  TcpServer
{
	
	static LogHelper loghelper = new LogHelper("tcpServerLogger");
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		// use hashmap to store key-value pairs
		HashMap<String, String> hashmap = new HashMap<String, String>();
		
		try{
			
			int port_num = new Integer(args[0]);
			serverSocket = new ServerSocket(port_num);
			
			while(true)
			{
				Socket clientSocket = serverSocket.accept();

				String ip = clientSocket.getInetAddress().getHostAddress();
				//int port_num = clientSocket.getPort();
				
				//System.out.println(ip+".....connected");

				DataInputStream in = new DataInputStream( clientSocket.getInputStream() );
				DataOutputStream out = new DataOutputStream( clientSocket.getOutputStream() );
				
				String in_data = in.readUTF();
				// response to PUT/GET/DEL request
				String out_data = UtilTool.requestHandler(in_data, hashmap,ip,clientSocket.getPort(),loghelper);
				
				if( out_data != null )
					out.writeUTF(out_data);
					
			}
	
		} catch ( EOFException e ){
				loghelper.record("[ERROR]	EOFException Exception");
				System.out.println("EOF Exception: " + e.getMessage());

		} catch ( IOException e ){
				loghelper.record("[ERROR]	IO Exception");
				System.out.println("IO Exception: " + e.getMessage());

		}catch ( ArrayIndexOutOfBoundsException e) {
					// log
					// basic validation for args[0] and args[1]
				loghelper.record("[ERROR]	Invalid Input IP/Port Number!");
				System.out.println("Invalid Input IP/Port Number!");

		} finally {
				if( serverSocket != null )
					try {	
							serverSocket.close();
					} catch (IOException e){
								loghelper.record("[ERROR]	Socket Close Failed!");
								System.out.println("socket close failed");
					}
		}
	}
}

