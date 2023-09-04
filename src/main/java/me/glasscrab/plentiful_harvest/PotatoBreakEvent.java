package me.glasscrab.plentiful_harvest;

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

public class PotatoBreakEvent implements Listener {

    @EventHandler
    public void onPotatoesBreak(BlockDropItemEvent e) {
        if (e.getItems().size() < 1) return;
        if(!e.getBlockState().getType().equals(Material.POTATOES)) return;
        Ageable age = (Ageable) e.getBlockState().getBlockData();
        if(age.getAge() != age.getMaximumAge()){
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 3 || handItem.getItemMeta().getCustomModelData() == 103)){
                e.getBlock().setType(Material.POTATOES);
                e.getBlock().setBlockData(age);
                for(Item droppedItem : e.getItems()){
                    droppedItem.remove();
                }
                return;
            }
            else{
                return;
            }
        }

        if(!e.getItems().get(0).getItemStack().getType().equals(Material.POTATO)) return;
        if(e.getBlockState() instanceof Container) return;

        int chance = 300;
        int rand = (int) (Math.random() * chance) + 1;
        int jackpot = 256;
        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 3){
            for(Item droppedItem : e.getItems()){
                droppedItem.remove();
                droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                e.getPlayer().getInventory().addItem(droppedItem.getItemStack());
            }
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.POTATOES);
                    e.getBlock().setBlockData(ageable);
                }
            }.runTaskLater(Plentiful_harvest.plentiful_harvest, 1);
        }
        else if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && handItem.getItemMeta().getCustomModelData() == 103){
            for(Item droppedItem : e.getItems()){
                droppedItem.remove();
            }
            Ageable ageable = (Ageable) e.getBlockState().getBlockData();
            ageable.setAge(0);

            new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.POTATOES);
                    e.getBlock().setBlockData(ageable);
                }
            }.runTaskLater(Plentiful_harvest.plentiful_harvest, 1);
        }
        if (rand != jackpot) return;
        e.getPlayer().sendMessage(ChatColor.GREEN + "You harvested a Medicinal Potato!");
        List<String> potatoLore = new ArrayList<>();
        potatoLore.add(ChatColor.GRAY + "Opposite to the poisonous potato, and much rarer.");
        ItemStack superCrop = Manager.getManager().makeSuperCrop(ChatColor.GREEN + "Medicinal Potato",Material.POTATO,potatoLore,1);

        if(handItem.getType().equals(Material.WOODEN_HOE) && handItem.getItemMeta() != null && (handItem.getItemMeta().getCustomModelData() == 3 || handItem.getItemMeta().getCustomModelData() == 103)){
            e.getPlayer().getInventory().addItem(superCrop);
            if(e.getPlayer().getInventory().firstEmpty() != -1) return;
            if(!Manager.getManager().isFull(e.getPlayer(),superCrop)) return;
            Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(),superCrop);
            superCropItem.setGlowing(true);
        }
        else{
            Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(),superCrop);
            superCropItem.setGlowing(true);
        }
    }
}