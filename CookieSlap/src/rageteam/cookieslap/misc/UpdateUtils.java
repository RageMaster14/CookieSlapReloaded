package rageteam.cookieslap.misc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import rageteam.cookieslap.main.CookieSlap;

public class UpdateUtils implements Listener {

	@EventHandler
	public void updateJoin(PlayerJoinEvent e){
		
		Player player = e.getPlayer();
		Permissions perms = new Permissions(player);
		
		if(perms.isAdmin()){
			if(CookieSlap.getCookieSlap().updateOut){
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&bCookieSlap update is available: &d" + CookieSlap.getCookieSlap().newVer + "");
				CookieSlap.getCookieSlap().chat.sendMessage(player, "b to update type /cs update");
			} else if(CookieSlap.getCookieSlap().getConfig().getBoolean("auto-update")) {
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&b No update found");
			}
		}
	}
}
