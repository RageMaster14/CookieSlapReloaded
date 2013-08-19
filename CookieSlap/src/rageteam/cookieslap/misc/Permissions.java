package rageteam.cookieslap.misc;

import org.bukkit.entity.Player;

public class Permissions {
	
	Player player;
	
	public Permissions(Player player) {
		this.player = player;
	}
	
	public boolean isAdmin() {
		return player.isOp() || player.hasPermission("cookieslap.admin");
	}
	
	public boolean canjoin() {
		return player.hasPermission("cookieslap.join") || isAdmin();
	}
	
	public boolean canModifyMaps() {
		return player.hasPermission("cookieslap.maps") || isAdmin();
	}
	
	public boolean canStartEnd() {
		return player.hasPermission("cookieslap.startend") || isAdmin();
	}
	
    public boolean canjoinFull() {
    	return player.hasPermission("cookieslap.joifull") || isAdmin();
    }
    
    public boolean canShop() {
    	return player.hasPermission("cookieslap.shop") || isAdmin();
    }


}