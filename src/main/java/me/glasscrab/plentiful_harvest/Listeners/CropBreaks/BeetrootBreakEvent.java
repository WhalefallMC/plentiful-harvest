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
            
            if(!manager.isCustomHoe(handItem, Material.BEETROOT)) {
                return;
            }

            manager.replant(e.getBlock(), Material.NETHER_WART, age, e.getItems());
            return;
        }

        for (Item dropItem : e.getItems()) {
            if(!(dropItem.getItemStack().getType().equals(Material.BEETROOT))) continue;
            if(e.getBlockState() instanceof Container) return;

            int chance = 300;
            int rand = (int) (Math.random() * chance) + 1;
            int jackpot = 256;

            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            if(manager.isCustomHoeOne(Material.BEETROOT, handItem)) {
                for(Item droppedItem : e.getItems()) {
                    droppedItem.remove();
                    if(droppedItem.getItemStack().getType().equals(Material.BEETROOT_SEEDS)) {
                        droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                    }
                    
                    e.getPlayer().getInventory().addItem(droppedItem.getItemStack());
                }
                
                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(e.getBlock(), Material.BEETROOTS, ageable);
            } else if(manager.isCustomHoeTwo(Material.BEETROOT, handItem)) {
                for(Item droppedItem : e.getItems()){
                    droppedItem.remove();
                }
                
                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(e.getBlock(), Material.BEETROOTS, ageable);
            }
            
            if(rand != jackpot) return;
            
            e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "You harvested a Mystic Beetroot!");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "A beetroot so old it's been infused with magic.");
            ItemStack superCrop = manager.makeSuperCrop(ChatColor.LIGHT_PURPLE+ "Mystic Beetroot", Material.BEETROOT, lore, 1);

            if(manager.isCustomHoe(handItem, Material.BEETROOT)) {
                manager.giveSuperCrop(e.getPlayer(), superCrop, e);
            } else {
                Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(), superCrop);
                superCropItem.setGlowing(true);
            }
        }
    }
}
