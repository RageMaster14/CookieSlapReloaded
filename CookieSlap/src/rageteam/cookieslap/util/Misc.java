package rageteam.cookieslap.util;

import java.util.Calendar;

import rageteam.cookieslap.main.CS;

public class Misc {
	CS plugin;
	public Misc(CS instance) {this.plugin = instance;}
	
	@SuppressWarnings("static-access")
	public String getTime() {
		Calendar calendar = Calendar.getInstance();
		String hours = Integer.toString(calendar.HOUR_OF_DAY);
		String minutes = Integer.toString(calendar.MINUTE);
		String seconds = Integer.toString(calendar.SECOND);
		String day = Integer.toString(calendar.DAY_OF_MONTH);
		String month = Integer.toString(calendar.MONTH + 1);
		String year = Integer.toString(calendar.YEAR);
		
		return day + "/" + month + "/" + year + " - " + hours + ":" + minutes + ":" + seconds;		
	}
}
