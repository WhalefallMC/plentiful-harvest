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
import org.bukkit.entity.Player;
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

    /*  Function: When a crop breaks, run the function.
        Parameters: BlockDropItemEvent e
        Return: void */
    @EventHandler
    public void onCropBreak(BlockDropItemEvent event) {
        if(event.getItems().isEmpty()) return; // If there are no items in inventory, return
        Player player = event.getPlayer();

        if(!manager.isCropBlock(event.getBlockState().getType())) return; // If the block is not a crop block, return
        if(event.getBlockState() instanceof Container) return; // If the block is a container, return

        Ageable age = (Ageable) e.getBlockState().getBlockData();

        // If the crop is fully grown and is harvested by a custom hoe, replant the crop
        // return if the crop isnt fully grown or if the crop is harvested by a non-custom hoe
        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = player.getInventory().getItemInMainHand();

            if(!manager.isCustomHoe(handItem)) {
                return;
            }

            manager.replant(event.getBlock(), event.getBlockState().getType(), age, event.getItems());
            return;
        }

        // If the player inventory is full, send a message to the player and play a sound
        if(manager.isFull(player)){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED+"YOUR INVENTORY IS FULL! YOU CANNOT COLLECT SUPER CROPS!"));
            player.playSound(player, Sound.BLOCK_BELL_USE, 0.6f, 1);
        }


        for (Item dropItem : event.getItems()) {
            // Chance to get a super crop
            int chance = 300;
            int rand = (int) (Math.random() * chance) + 1;
            int jackpot = 256;

            ItemStack handItem = player.getInventory().getItemInMainHand();
            if(manager.isCustomHoeOne(handItem)) {
                for(Item droppedItem : event.getItems()) {
                    droppedItem.remove();
                    if(manager.isCropSeed(droppedItem.getItemStack().getType())) {
                        droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                    }

                    player.getInventory().addItem(droppedItem.getItemStack());
                }

                Ageable ageable = (Ageable) event.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(event.getBlock(), event.getBlockState().getType(), ageable);
            } else if(manager.isCustomHoeTwo(handItem)) {
                for(Item droppedItem : event.getItems()){
                    droppedItem.remove();
                }

                Ageable ageable = (Ageable) event.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(event.getBlock(), event.getBlockState().getType(), ageable);
            }

            if(rand != jackpot) return;

            player.sendMessage(manager.cropBlockMessage(event.getBlockState().getType()));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + manager.cropBlockLore(event.getBlockState().getType()));
            ItemStack superCrop = manager.makeSuperCrop(manager.cropBlockName(event.getBlockState().getType()), manager.cropBlockToItem(event.getBlockState().getType()), lore, 1, 1);

            if(manager.isCustomHoe(handItem)) {
                manager.giveSuperCrop(player, superCrop);
            } else {
                Item superCropItem = event.getItems().get(0).getWorld().dropItem(event.getItems().get(0).getLocation(), superCrop);
                superCropItem.setGlowing(true);
            }
        }
    }
}
