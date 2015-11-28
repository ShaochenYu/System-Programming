import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


class  TcpServer
{
	
	static LogHelper loghelper = new LogHelper("tcpServerLogger");
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		// use hashmap to store key-value pairs
		HashMap<String, String> hashmap = new HashMap<String, String>();
		// thread pool to manage threads
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		
		try{
			
			int port_num = new Integer(args[0]);
			serverSocket = new ServerSocket(port_num);
			
			while(true)
			{
				Socket clientSocket = serverSocket.accept();
				// start a thread
				executor.execute( new TcpConnection( clientSocket, loghelper, hashmap) );

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

		}catch ( Exception e) {
						// EOFException
						loghelper.record("	[ERROR]	Ohter Exception!");
						System.out.println("Other Exception!" + e.getMessage());

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

