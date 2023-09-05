package me.glasscrab.plentiful_harvest.Listeners.CropBreaks;

import me.glasscrab.plentiful_harvest.Manager;
import me.glasscrab.plentiful_harvest.PlentifulHarvest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CarrotBreakEvent implements Listener {
    private final Manager manager;
    
    public CarrotBreakEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onCarrotBreak(BlockDropItemEvent e) {
        if (e.getItems().isEmpty()) return;
        if(!e.getBlockState().getType().equals(Material.CARROTS)) return;
        
        Ageable age = (Ageable) e.getBlockState().getBlockData();
        
        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            
            if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 2 || handItem.getItemMeta().getCustomModelData() == 102)) {
                e.getBlock().setType(Material.CARROTS);
                e.getBlock().setBlockData(age);
                
                for(Item droppedItem : e.getItems()){
                    droppedItem.remove();
                }
            }
            return;
        }

        if(!e.getItems().get(0).getItemStack().getType().equals(Material.CARROT)) return;
        if(e.getBlockState() instanceof Container) return;

        int chance = 300;
        int rand = (int) (Math.random() * chance) + 1;
        int jackpot = 256;
        
        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 2) {
            manager.giveDroppedItems(e.getPlayer(), e.getItems());
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.CARROTS);
                    e.getBlock().setBlockData(ageable);
                }
            }.runTaskLater(PlentifulHarvest.INSTANCE, 1);
        } else if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 102) {
            for(Item droppedItem : e.getItems()) {
                droppedItem.remove();
            }
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.CARROTS);
                    e.getBlock().setBlockData(ageable);
                }
            }.runTaskLater(PlentifulHarvest.INSTANCE, 1);
        }
        
        if (rand != jackpot) return;
        
        e.getPlayer().sendMessage(ChatColor.GOLD + "You harvested a Hyper Carrot!");
        List<String> carrotLore = new ArrayList<>();
        carrotLore.add(ChatColor.GRAY + "A single one could feed 100 horses.");
        ItemStack superCrop = manager.makeSuperCrop(ChatColor.GOLD + "Hyper Carrot",Material.CARROT,carrotLore,1);

        if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 2 || handItem.getItemMeta().getCustomModelData() == 102)) {
            manager.giveSuperCrop(e.getPlayer(), superCrop, e);
        } else {
            Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(),superCrop);
            superCropItem.setGlowing(true);
        }
    }
}