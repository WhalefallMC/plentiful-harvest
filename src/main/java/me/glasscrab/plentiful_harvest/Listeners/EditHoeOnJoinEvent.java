package me.glasscrab.plentiful_harvest.Listeners;

import me.glasscrab.plentiful_harvest.Manager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class EditHoeOnJoinEvent implements Listener {

    private final Manager manager;

    public EditHoeOnJoinEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void editHoeOnJoin(PlayerJoinEvent e){
        ItemStack[] playerInventory  = e.getPlayer().getInventory().getContents();
        int i = 0;
        for(ItemStack item: playerInventory){
            i = i+1;
            if(item == null) continue;
            if(!manager.isOldHoe(item)) continue;
            ItemStack farmersHoe = new ItemStack(Material.WOODEN_HOE);
            ItemMeta farmersHoeMeta = farmersHoe.getItemMeta();
            farmersHoeMeta.setDisplayName(ChatColor.GOLD+"Farmer's Hoe");
            farmersHoeMeta.setLore(List.of(ChatColor.GRAY+"New technology allows for automatic",ChatColor.GRAY+"replanting of crops.","",ChatColor.GRAY+"Void Normal Crops: "+ChatColor.RED+"OFF"));
            farmersHoeMeta.setCustomModelData(2767);
            farmersHoeMeta.setUnbreakable(true);
            farmersHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            farmersHoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            farmersHoe.setItemMeta(farmersHoeMeta);
            e.getPlayer().getInventory().setItem(Arrays.asList(playerInventory).indexOf(item),farmersHoe);
        }
    }
}
