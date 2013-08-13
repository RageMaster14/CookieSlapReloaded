package rageteam.cookieslap.util;


import rageteam.cookieslap.main.CS;

public class Logger {
	CS plugin;
	public Logger(CS instance) { this.plugin = instance; }
	
	public void log(boolean warning, String message) {
		if(warning) { }
		else { }
	}
	
	public void logError(Exception ex) {
		log(true, "Error");
	}
}
