package me.glasscrab.plentiful_harvest.Listeners.CropBreaks;

import me.glasscrab.plentiful_harvest.Manager;
import me.glasscrab.plentiful_harvest.Plentiful_harvest;
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

public class BeetrootBreakEvent implements Listener {
    private final Manager manager;
    
    public BeetrootBreakEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBeetrootBreak(BlockDropItemEvent e) {
        if(e.getItems().isEmpty()) return;
        if(!e.getBlockState().getType().equals(Material.BEETROOTS)) return;
        
        Ageable age = (Ageable) e.getBlockState().getBlockData();
        
        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            
            if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 4 || handItem.getItemMeta().getCustomModelData() == 104)) {
                e.getBlock().setType(Material.BEETROOTS);
                e.getBlock().setBlockData(age);
                
                for(Item droppedItem : e.getItems()) {
                    droppedItem.remove();
                }
                return;
            }
            return;
        }

        for (Item dropItem : e.getItems()) {
            if(!(dropItem.getItemStack().getType().equals(Material.BEETROOT))) continue;
            if(e.getBlockState() instanceof Container) return;

            int chance = 300;
            int rand = (int) (Math.random() * chance) + 1;
            int jackpot = 256;

            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 4) {
                for(Item droppedItem : e.getItems()) {
                    droppedItem.remove();
                    if(droppedItem.getItemStack().getType().equals(Material.BEETROOT_SEEDS)) {
                        droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                    }
                    
                    e.getPlayer().getInventory().addItem(droppedItem.getItemStack());
                }
                
                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEETROOTS);
                        e.getBlock().setBlockData(ageable);
                    }
                }.runTaskLater(Plentiful_harvest.plentiful_harvest, 1);
            } else if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 104) {
                for(Item droppedItem : e.getItems()){
                    droppedItem.remove();
                }
                
                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.BEETROOTS);
                        e.getBlock().setBlockData(ageable);
                    }
                }.runTaskLater(Plentiful_harvest.plentiful_harvest, 1);
            }
            
            if(rand != jackpot) return;
            
            e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "You harvested a Mystic Beetroot!");
            List<String> beetrootLore = new ArrayList<>();
            beetrootLore.add(ChatColor.GRAY + "A beetroot so old it's been infused with magic.");
            ItemStack superCrop = manager.makeSuperCrop(ChatColor.LIGHT_PURPLE+ "Mystic Beetroot",Material.BEETROOT,beetrootLore,1);

            if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 4 || handItem.getItemMeta().getCustomModelData() == 104)) {
                manager.giveSuperCrop(e.getPlayer(), superCrop, e);
            } else {
                Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(),superCrop);
                superCropItem.setGlowing(true);
            }
        }
    }
}
