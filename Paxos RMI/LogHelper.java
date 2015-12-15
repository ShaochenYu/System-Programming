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
		
		logger = Logger.getLogger(filename);
		logger.setUseParentHandlers(false);
		FileHandler fh;
		
		try {

	        fh = new FileHandler(System.getProperty("user.dir") + "/" + filename);  
	        // set formatter for log
			fh.setFormatter(new Formatter(){
				public String format( LogRecord rec ){
					StringBuffer buf = new StringBuffer();
					buf.append( df.format(new Date(rec.getMillis())) ).append("		");
					buf.append( rec.getMessage() ).append("\n");
					return buf.toString();
				}
				
			}
			);
	
			logger.addHandler(fh);
			
	    } catch (SecurityException e) {
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	
	}
	
	public void record(String info){
		
		logger.info( info );
		
	}

}

