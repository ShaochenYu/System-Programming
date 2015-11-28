import java.util.*; 
import java.net.*;
import java.io.*;
//import java.io.File;

// Independent thread to handle a connection
class  TcpConnection implements Runnable
{	
	Socket clientSocket;
	LogHelper loghelper;
	HashMap<String, String> hashmap;
	
	public TcpConnection(Socket clientSocket, LogHelper loghelper, HashMap<String, String> hashmap) throws Exception{
		this.clientSocket = clientSocket;
		this.loghelper = loghelper;
		this.hashmap = hashmap;
		this.run();
	}
	
	public void run(){
		try{
		String ip = clientSocket.getInetAddress().getHostAddress();

		DataInputStream in = new DataInputStream( clientSocket.getInputStream() );
		DataOutputStream out = new DataOutputStream( clientSocket.getOutputStream() );
		
		String in_data = in.readUTF();
		// response to PUT/GET/DEL request
		String out_data = UtilTool.requestHandler(in_data, hashmap,ip,clientSocket.getPort(),loghelper);
		
		if( out_data != null )
			out.writeUTF(out_data);
		}catch(IOException e){
			loghelper.record("[ERROR]	IO Exception");
			System.out.println("IO Exception: " + e.getMessage());
		}
	
	}
	
}

