package rageteam.cookieslap.economy;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import rageteam.cookieslap.main.CookieSlap;

public class PopupMenu implements InventoryHolder {
	
	CookieSlap plugin;
	public PopupMenu(CookieSlap instance) { this.plugin = instance; }

    private HashMap<Integer, MenuItem> items = new HashMap<>();
    private Inventory inventory;
    private String title;
    private int size;
    private boolean exitOnClickOutside = true;
    private MenuCloseBehaviour menuCloseBehaviour;

    public PopupMenu(String title, int rows) {
        this.title = title;
        this.size = rows * 9;
    }

    public void setMenuCloseBehaviour(MenuCloseBehaviour menuCloseBehaviour) {
        this.menuCloseBehaviour = menuCloseBehaviour;
    }

    public MenuCloseBehaviour getMenuCloseBehaviour() {
        return menuCloseBehaviour;
    }

    public void setExitOnClickOutside(boolean exit) {
        this.exitOnClickOutside = exit;
    }

    public boolean addMenuItem(MenuItem item, int x, int y) {
        return addMenuItem(item, y * 9 + x);
    }

    public boolean addMenuItem(MenuItem item, int index) {
        ItemStack slot = getInventory().getItem(index);
        if (slot != null && slot.getType() != Material.AIR) {
            return false;
        }
        getInventory().setItem(index, item.getItemStack());
        items.put(index, item);
        item.addToMenu(this);
        return true;
    }

    public boolean removeMenuItem(int x, int y) {
        return removeMenuItem(y * 9 + x);
    }

    public boolean removeMenuItem(int index) {
        ItemStack slot = getInventory().getItem(index);
        if (slot == null || slot.getTypeId() == 0) {
            return false;
        }
        getInventory().clear(index);
        items.remove(index).removeFromMenu(this);
        return true;
    }

    protected void selectMenuItem(Player player, int index) {
        if (items.containsKey(index)) {
            MenuItem item = items.get(index);
            item.onClick(player);
        }
    }

    public void openMenu(Player player) {
        if (getInventory().getViewers().contains(player)) {
            throw new IllegalArgumentException(player.getName() + " is already viewing " + getInventory().getTitle());
        }
        player.openInventory(getInventory());
    }

    public void closeMenu(Player player) {
        if (getInventory().getViewers().contains(player)) {
            InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());
            Bukkit.getPluginManager().callEvent(event);
            player.closeInventory();
            getInventory().getViewers().remove(player);
        }
    }
    
    public void switchMenu(Player player, PopupMenu toMenu) {
        PopupMenuAPI.switchMenu(player, this, toMenu);
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(this, size, title);
        }
        return inventory;
    }

    public boolean exitOnClickOutside() {
        return exitOnClickOutside;
    }

    @Override
    protected PopupMenu clone() {
        PopupMenu clone = new PopupMenu(title, size);
        clone.setExitOnClickOutside(exitOnClickOutside);
        for (int index : items.keySet()) {
            addMenuItem(items.get(index), index);
        }
        return clone;
    }

    @SuppressWarnings("deprecation")
	public void updateMenu() {
        for (HumanEntity entity : getInventory().getViewers()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.updateInventory();
            }
        }
    }
}
//test
