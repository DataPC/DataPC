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
	private FileHandler logFile;
	private SimpleFormatter formatter;
	
	private Logging() throws IOException {
        try {
        	LOG = Logger.getLogger("ServerLog");
        	
        	logFile = new FileHandler("C:\\Users\\halas\\Desktop\\logger.log", true);
        	LOG.addHandler(logFile);
        	
        	formatter = new SimpleFormatter();
        	logFile.setFormatter(formatter);
        	
        	LOG.info("Start loggera uspesny");
        } catch (SecurityException e) {
        	// Kedze logger sa nepodarilo spustit, tak nemame kam zalogovat exception
            e.printStackTrace();
        }
	}
	
	public static Logger getLogger() {
		if (LOG == null) {
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
