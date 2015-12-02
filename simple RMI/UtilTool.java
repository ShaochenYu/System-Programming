import java.util.*; 
//import java.io.File;

class  UtilTool
{	
	
	public static String requestHandler(String in_data, HashMap<String, String> hashmap, String ip, int port_num, LogHelper loghelper){
		
		if( in_data == null || in_data.length() < 3 )	{
				// receive malformed request
				loghelper.record("	[WARNING]	RECEIVED MALFORMED REQUEST FROM <" + ip + ">:<" + port_num + ">");
				return null;
			}
		String[] data = in_data.split("/");
		StringBuffer res = new StringBuffer();
		
		switch( data[0] ){
			

			
			default:{	
						loghelper.record("	[WARNING]	RECEIVED MALFORMED FROM <" + ip + ">:<" + port_num + ">");
						return "";
			
			}
		}
		
		
	}
}

