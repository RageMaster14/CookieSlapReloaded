package RageTeam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameLoop implements Runnable {

	@Override
	public void run() {
		if (CookieMain.timeInSeconds >=60 && CookieMain.timeInSeconds % 60 == 0){
			broadcastTimeUntilStart(true);
		}
		if (CookieMain.timeInSeconds <=30 && CookieMain.timeInSeconds % 15 == 0 && CookieMain.timeInSeconds > 0){
			broadcastTimeUntilStart(true);
		}
		if (CookieMain.timeInSeconds <=10 && CookieMain.timeInSeconds >0){
			broadcastTimeUntilStart(false);
			resetNote();
		}
		if (CookieMain.timeInSeconds == 0){
			if (CookieMain.canStart){
				Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "CookieSlap" + ChatColor.GRAY + "] The Game Has Begun!" );
			}else{
					Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "CookieSlap" + ChatColor.GRAY + "] Not Enough Players, Restarting Timer!");
					note();
					CookieMain.timeInSeconds = 121;
			}
		}
		if(CookieMain.timeInSeconds > 0 ){
			CookieMain.timeInSeconds--;
		}
	}

	private void resetNote() {
		for(Player player : Bukkit.getOnlinePlayers()){
			player.playSound(player.getLocation(), Sound.CLICK, 10, 1);
		}
		
	}

	private void note() {
		for(final Player player : Bukkit.getOnlinePlayers()){
			final Location l = player.getLocation();
			new Thread(new Runnable(){
				public void run(){
					try{
						for(int i = 0; i < 4; i++) {
							player.playSound(l, Sound.NOTE_BASS_GUITAR, 10, 1);
							Thread.sleep(450);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}

	private void broadcastTimeUntilStart(boolean b) {
		if(CookieMain.timeInSeconds == 120){
			Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "CookieSlap" + ChatColor.GRAY + "] 2 Minutes Until The Game Starts!");
		}else
			if(CookieMain.timeInSeconds == 60){
				Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "CookieSlap" + ChatColor.GRAY + "] 1 Minute Until The Game Starts!");
		}else
			if(CookieMain.timeInSeconds < 60){
				Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "CookieSlap" + ChatColor.GRAY + "] " + CookieMain.timeInSeconds + " Seconds Until The Game Starts!");
		}
	}
}
