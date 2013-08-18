package rageteam.cookieslap.economy;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import rageteam.cookieslap.main.CookieSlap;

public abstract class MenuItem {
	
	CookieSlap plugin;
	public MenuItem(CookieSlap instance) { this.plugin = instance; }
	
    private PopupMenu menu;
    private int number;
    private MaterialData icon;
    private String text;
    private List<String> descriptions = new ArrayList<>();

    public MenuItem(String text) {
        this(text, new MaterialData(Material.EMERALD));
    }

    public MenuItem(String text, MaterialData icon) {
        this(text, icon, 1);
    }

    public MenuItem(String text, MaterialData icon, int number) {
        this.text = text;
        this.icon = icon;
        this.number = number;
    }

    protected void addToMenu(PopupMenu menu) {
        this.menu = menu;
    }

    protected void removeFromMenu(PopupMenu menu) {
        if (this.menu == menu) {
            this.menu = null;
        }
    }

    public PopupMenu getMenu() {
        return menu;
    }

    public int getNumber() {
        return number;
    }

    public MaterialData getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }


    public void setDescriptions(List<String> lines) {
        descriptions = lines;
    }

    public void addDescription(String line) {
        descriptions.add(line);
    }

    protected ItemStack getItemStack() {
        ItemStack slot = new ItemStack(getIcon().getItemType(), getNumber(), getIcon().getData());
        ItemMeta meta = slot.getItemMeta();
        meta.setLore(descriptions);
        meta.setDisplayName(getText());

        slot.setItemMeta(meta);
        return slot;
    }

    public abstract void onClick(Player player);
}
//test