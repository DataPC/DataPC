package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Trieda loggera implementovana ako Singleton
 * @author DataPC
 */
public class Logging {
	
	private static Logger LOG = null;
	
	private Logging() throws IOException {
		FileHandler logFile;  

        try {  
        	logFile = new FileHandler("logger.log");
            LOG = Logger.getLogger("ServerLog");
        	LOG.addHandler(logFile);
            SimpleFormatter formatter = new SimpleFormatter();  
            logFile.setFormatter(formatter);  
            LOG.info("Start loggera uspesny"); 
        } 
        catch (SecurityException e) {  
            e.printStackTrace();
            LOG.severe("Start loggera neuspesny");
        } 
	}
	
	
	public static Logger getLogger() {
		if(LOG == null) {
			try {
				new Logging();
			} catch (IOException e) {
				// Kedze logger sa nepodarilo spustit, tak nemame kam zalogovat exception
				e.printStackTrace();
			}
		}
		return LOG;
	}
}