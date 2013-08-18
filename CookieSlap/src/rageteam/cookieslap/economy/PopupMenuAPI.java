package rageteam.cookieslap.economy;


import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import rageteam.cookieslap.main.CookieSlap;

public class PopupMenuAPI extends JavaPlugin implements Listener {
	CookieSlap plugin;
	public PopupMenuAPI(CookieSlap instance) { this.plugin = instance; }
    
    private static PopupMenuAPI instance;
    
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
    }

    public static PopupMenu createMenu(String title, int rows) {
        return new PopupMenu(title, rows);
    }

    public static PopupMenu cloneMenu(PopupMenu menu) {
        return menu.clone();
    }

    public static void removeMenu(PopupMenu menu) {
        for (HumanEntity viewer : menu.getInventory().getViewers()) {
            if (viewer instanceof Player) {
                menu.closeMenu((Player) viewer);
            } else {
                viewer.closeInventory();
            }
        }
    }

    public static void switchMenu(final Player player, PopupMenu fromMenu, final PopupMenu toMenu) {
        fromMenu.closeMenu(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                toMenu.openMenu(player);
            }
        }.runTask(instance);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
    	event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof PopupMenu) {
            PopupMenu menu = (PopupMenu) inventory.getHolder();
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                    // Quick exit for a menu, click outside of it
                    if (menu.exitOnClickOutside()) {
                        menu.closeMenu(player);
                    }
                } else {
                    int index = event.getRawSlot();
                    if (index < inventory.getSize()) {
                        menu.selectMenuItem(player, index);
                    } else {
                        // If they want to mess with their inventory they don't need to do so in a menu
                        if (menu.exitOnClickOutside()) {
                            menu.closeMenu(player);
                        }
                    }
                }
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMenuClosed(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Inventory inventory = event.getInventory();
            if (inventory.getHolder() instanceof PopupMenu) {
                PopupMenu menu = (PopupMenu) inventory.getHolder();
                MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();
                if (menuCloseBehaviour != null) {
                    menuCloseBehaviour.onClose((Player) event.getPlayer());
                }
            }
        }
    }
}
//test