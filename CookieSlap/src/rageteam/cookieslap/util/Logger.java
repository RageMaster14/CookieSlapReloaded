package rageteam.cookieslap.util;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

import rageteam.cookieslap.main.Info;
import rageteam.cookieslap.main.CS;

public class Logger {
	CS plugin;
	public Logger(CS instance) { this.plugin = instance; }
	
	private void logToFile(File destination, String message) {		
		try {
			FileWriter writer = new FileWriter(destination, true);
			writer.write(message + "\n");
			writer.close();
			
		} catch(Exception ex) {
			plugin.logger.logError(ex);
		}
	}
	
	public void log(boolean warning, String message) {
		Level level;
		if(warning) { level = Level.SEVERE; }
		else { level = Level.INFO; }
				
		plugin.getLogger().log(level, message);
		logToFile(Info.logFile, message);
	}
	
	public void logError(Exception ex) {
		log(true, "Error");
		
		String message =
				"--------------------| ERROR |---------------------\n" +
				"-----( Error Message )-----\n" + ex.getMessage() + "\n\n\n\n\n";
		
		logToFile(Info.errorlogFile, message);		
	}
}
