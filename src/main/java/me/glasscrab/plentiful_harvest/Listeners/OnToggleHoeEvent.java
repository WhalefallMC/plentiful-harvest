package me.glasscrab.plentiful_harvest.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OnToggleHoeEvent implements Listener {

    @EventHandler
    public void onToggleHoe(PlayerInteractEvent e){
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.PHYSICAL)) return;
        if(e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if(!e.getPlayer().isSneaking()) return;
        if(e.getItem() == null) return;
        if(!e.getItem().getType().equals(Material.WOODEN_HOE)) return;
        if(!e.getItem().hasItemMeta()) return;
        if(e.getItem().getItemMeta() == null) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() == 2767 || e.getItem().getItemMeta().getCustomModelData() == 2768){
            e.setCancelled(true);
            if(e.getItem().getItemMeta().getCustomModelData() == 2767){
                ItemStack farmersVoidHoe = new ItemStack(Material.WOODEN_HOE);
                ItemMeta farmersVoidHoeMeta = farmersVoidHoe.getItemMeta();
                farmersVoidHoeMeta.setDisplayName(ChatColor.GOLD+"Farmer's Voiding Hoe");
                farmersVoidHoeMeta.setLore(List.of(ChatColor.GRAY+"New technology allows for automatic",ChatColor.GRAY+"replanting of crops.","",ChatColor.GRAY+"Void Normal Crops: "+ChatColor.GREEN+"ON"));
                farmersVoidHoeMeta.setCustomModelData(2768);
                farmersVoidHoeMeta.setUnbreakable(true);
                farmersVoidHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                farmersVoidHoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

                farmersVoidHoe.setItemMeta(farmersVoidHoeMeta);

                e.getPlayer().getInventory().setItemInMainHand(farmersVoidHoe);
            }
            if(e.getItem().getItemMeta().getCustomModelData() == 2768){
                ItemStack farmersHoe = new ItemStack(Material.WOODEN_HOE);
                ItemMeta farmersHoeMeta = farmersHoe.getItemMeta();
                farmersHoeMeta.setDisplayName(ChatColor.GOLD+"Farmer's Hoe");
                farmersHoeMeta.setLore(List.of(ChatColor.GRAY+"New technology allows for automatic",ChatColor.GRAY+"replanting of crops.","",ChatColor.GRAY+"Void Normal Crops: "+ChatColor.RED+"OFF"));
                farmersHoeMeta.setCustomModelData(2767);
                farmersHoeMeta.setUnbreakable(true);
                farmersHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                farmersHoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

                farmersHoe.setItemMeta(farmersHoeMeta);
                e.getPlayer().getInventory().setItemInMainHand(farmersHoe);
            }
        }
    }
}
