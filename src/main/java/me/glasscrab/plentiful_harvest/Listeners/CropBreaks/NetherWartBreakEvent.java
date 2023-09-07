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

public class NetherWartBreakEvent implements Listener {
    private final Manager manager;
    
    public NetherWartBreakEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onNetherWartBreak(BlockDropItemEvent e) {
        if (e.getItems().isEmpty()) return;
        if(!e.getBlockState().getType().equals(Material.NETHER_WART)) return;
        
        Ageable age = (Ageable) e.getBlockState().getBlockData();
        
        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            
            if(!manager.isCustomHoe(handItem, Material.NETHER_WART)) {
                return;
            }

            manager.replant(e.getBlock(), Material.NETHER_WART, age, e.getItems());
            return;
        }

        if(!e.getItems().get(0).getItemStack().getType().equals(Material.NETHER_WART)) return;
        if(e.getBlockState() instanceof Container) return;

        int chance = 300;
        int rand = (int) (Math.random() * chance) + 1;
        int jackpot = 256;
        
        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();

        if(manager.isCustomHoeOne(Material.NETHER_WART, handItem)) {
            manager.giveDroppedItems(e.getPlayer(), e.getItems());
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            manager.replantLater(e.getBlock(), Material.NETHER_WART, ageable);
        } else if(manager.isCustomHoeTwo(Material.NETHER_WART, handItem)) {
            for(Item droppedItem : e.getItems()) {
                droppedItem.remove();
            }
            
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            manager.replantLater(e.getBlock(), Material.NETHER_WART, ageable);
        }
        
        if (rand != jackpot) return;
        
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You harvested a Warped Nether Wart!");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Like a four leaf clover, found very rarely.");
        ItemStack superCrop = manager.makeSuperCrop(ChatColor.DARK_AQUA + "Warped Nether Wart", Material.NETHER_WART, lore, 1);

        if(manager.isCustomHoeOne(Material.NETHER_WART, handItem)) {
            manager.giveSuperCrop(e.getPlayer(), superCrop, e);
        } else{
            Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(), superCrop);
            superCropItem.setGlowing(true);
        }
    }
}