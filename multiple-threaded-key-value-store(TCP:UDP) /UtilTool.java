import java.util.*; 
//import java.io.File;

class  UtilTool
{	
	// lock shared resource hashmap
	public static synchronized String requestHandler(String in_data, HashMap<String, String> hashmap, String ip, int port_num, LogHelper loghelper){
		
		if( in_data == null || in_data.length() < 3 )	{
				// receive malformed request
				loghelper.record("	[WARNING]	RECEIVED MALFORMED REQUEST FROM <" + ip + ">:<" + port_num + ">");
				return null;
			}
		String[] data = in_data.split("/");
		StringBuffer res = new StringBuffer();
		
		switch( data[0] ){
			
			case "PUT":{
						if( data.length < 3 || data[1].length() == 0 || data[2].length() == 0){
								loghelper.record("	[WARNING]	RECEIVED MALFORMED PUT REQUEST FROM <" + ip + ">:<" + port_num + ">");
								return null;
						}else{
							hashmap.put(data[1],data[2]);
							loghelper.record("	[MESSAGE]	RECEIVED PUT REQUEST FROM <" + ip + ">:<" + port_num + ">");
							return null;
						}
						
			}
			
			case "GET":{
						if( data.length < 2 ){
							loghelper.record("	[WARNING]	RECEIVED MALFORMED GET REQUEST FROM <" + ip + ">:<" + port_num + ">");
							return null;
						}else{
							if( hashmap.containsKey( data[1] ) ){
								loghelper.record("	[MESSAGE]	RECEIVED GET REQUEST FROM <" + ip + ">:<" + port_num + ">	RESPONSE: "+ hashmap.get(data[1]) );
								return hashmap.get(data[1]);
							}
							loghelper.record("	[WARNING]	RECEIVED GET REQUEST FOR NONE-EXISTENT KEY FROM <" + ip + ">:<" + port_num + ">");
							return "";
						}
			}
			
			case "DEL":{
						if(data.length < 2){
							loghelper.record("	[WARNING]	RECEIVED MALFORMED DELETE REQUEST FROM <" + ip + ">:<" + port_num + ">");
							return null;
						}else{
							loghelper.record("	[MESSAGE]	RECEIVED DELETE REQUEST FROM <" + ip + ">:<" + port_num + ">");
							hashmap.remove(data[1]);
						}
				
						return null;
						
			}
			
			default:{	
						loghelper.record("	[WARNING]	RECEIVED MALFORMED FROM <" + ip + ">:<" + port_num + ">");
						return "";
			
			}
		}
		
		
	}
}

