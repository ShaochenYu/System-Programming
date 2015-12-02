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

			
			default:{	
						loghelper.record("	[WARNING]	RECEIVED MALFORMED FROM <" + ip + ">:<" + port_num + ">");
						return "";
			
			}
		}
		
		
	}
}

