package me.glasscrab.plentiful_harvest.Listeners.CropBreaks;

import me.glasscrab.plentiful_harvest.Manager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Container;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CropBreakEvent implements Listener {
    private final Manager manager;

    public CropBreakEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onCropBreak(BlockDropItemEvent e) {
        if(e.getItems().isEmpty()) return;

        if(!manager.isCropBlock(e.getBlockState().getType())) return;
        if(e.getBlockState() instanceof Container) return;

        Ageable age = (Ageable) e.getBlockState().getBlockData();

        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();

            if(!manager.isCustomHoe(handItem)) {
                return;
            }

            manager.replant(e.getBlock(), e.getBlockState().getType(), age, e.getItems());
            return;
        }

        if(manager.isFull(e.getPlayer())){
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED+"YOUR INVENTORY IS FULL! YOU CANNOT COLLECT SUPER CROPS!"));
            e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_BELL_USE, 0.6f, 1);
        }

        for (Item dropItem : e.getItems()) {
            int chance = 300;
            int rand = (int) (Math.random() * chance) + 1;
            int jackpot = 256;

            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            if(manager.isCustomHoeOne(handItem)) {
                for(Item droppedItem : e.getItems()) {
                    droppedItem.remove();
                    if(manager.isCropSeed(droppedItem.getItemStack().getType())) {
                        droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                    }

                    e.getPlayer().getInventory().addItem(droppedItem.getItemStack());
                }

                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(e.getBlock(), e.getBlockState().getType(), ageable);
            } else if(manager.isCustomHoeTwo(handItem)) {
                for(Item droppedItem : e.getItems()){
                    droppedItem.remove();
                }

                Ageable ageable = (Ageable) e.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(e.getBlock(), e.getBlockState().getType(), ageable);
            }

            if(rand != jackpot) return;

            e.getPlayer().sendMessage(manager.cropBlockMessage(e.getBlockState().getType()));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + manager.cropBlockLore(e.getBlockState().getType()));
            ItemStack superCrop = manager.makeSuperCrop(manager.cropBlockName(e.getBlockState().getType()), manager.cropBlockToItem(e.getBlockState().getType()), lore, 1, 1);

            if(manager.isCustomHoe(handItem)) {
                manager.giveSuperCrop(e.getPlayer(), superCrop);
            } else {
                Item superCropItem = e.getItems().get(0).getWorld().dropItem(e.getItems().get(0).getLocation(), superCrop);
                superCropItem.setGlowing(true);
            }
        }
    }
}