package me.glasscrab.plentiful_harvest.Listeners.CropBreaks;

import me.glasscrab.plentiful_harvest.Manager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

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
            
            if(!manager.isCustomHoe(handItem, Material.CARROT)) {
                return;
            }

            manager.replant(e.getBlock(), Material.CARROT, age, e.getItems());
            return;
        }

        if(!e.getItems().get(0).getItemStack().getType().equals(Material.CARROT)) return;
        if(e.getBlockState() instanceof Container) return;

        int chance = 300;
        int rand = (int) (Math.random() * chance) + 1;
        int jackpot = 256;
        
        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        if(manager.isCustomHoeOne(Material.CARROT, handItem)) {
            manager.giveDroppedItems(e.getPlayer(), e.getItems());
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            manager.replantLater(e.getBlock(), Material.CARROT, ageable);
        } else if(manager.isCustomHoeTwo(Material.CARROT, handItem)) {
            for(Item droppedItem : e.getItems()) {
                droppedItem.remove();
            }
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            manager.replantLater(e.getBlock(), Material.CARROT, ageable);
        }
        
        if (rand != jackpot) return;
        
        e.getPlayer().sendMessage(ChatColor.GOLD + "You harvested a Hyper Carrot!");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A single one could feed 100 horses.");
        ItemStack superCrop = manager.makeSuperCrop(ChatColor.GOLD + "Hyper Carrot", Material.CARROT, lore, 1, 1);

        if(manager.isCustomHoe(handItem, Material.CARROT)) {
            manager.giveSuperCrop(e.getPlayer(), superCrop, e);
        } else {
            Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(),superCrop);
            superCropItem.setGlowing(true);
        }
    }
}