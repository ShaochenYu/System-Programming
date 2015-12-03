import java.util.logging.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.io.File;

class  LogHelper
{	
	Logger logger;
	// define date format
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
	
	public LogHelper( String filename ){
		

	
	}
	
	public void record(String info){
		
		logger.info( info );
		
	}

}

