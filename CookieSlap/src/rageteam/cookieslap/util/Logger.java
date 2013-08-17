package rageteam.cookieslap.util;


import rageteam.cookieslap.main.CookieSlap;

public class Logger {
	CookieSlap plugin;
	public Logger(CookieSlap instance) { this.plugin = instance; }
	
	public void log(boolean warning, String message) {
		if(warning) { }
		else { }
	}
	
	public void logError(Exception ex) {
		log(true, "Error");
	}
}
