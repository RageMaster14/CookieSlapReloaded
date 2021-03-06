package rageteam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class CookieSlapBoard extends JavaPlugin{
	
	public CookieSlapBoard(){
		setup();
	}
	
	private Scoreboard sb;
	
	public void setup(){
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = sb.registerNewObjective("side", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CookieSlap");
	}
	
	public Scoreboard getScoreboard(){
		if(sb == null){
			setup();
		}
		return sb;
	}
	
	private Objective getSidebar(){
		return getScoreboard().getObjective("side");
	}
	
	public void setDisplayName(String string){
		getSidebar().setDisplayName(string);
	}
	
	public void setScore(String name, int score){
		getSidebar().getScore(Bukkit.getOfflinePlayer(name)).setScore(score);
	}
	
	public void hideScore(String name){
		getScoreboard().resetScores(Bukkit.getOfflinePlayer(name));
	}
}
