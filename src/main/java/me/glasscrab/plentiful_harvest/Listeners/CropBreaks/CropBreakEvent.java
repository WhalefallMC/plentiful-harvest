package me.glasscrab.plentiful_harvest.Listeners.CropBreaks;

import me.glasscrab.plentiful_harvest.Manager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
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

        Ageable age = (Ageable) event.getBlockState().getBlockData();

        // If the crop is fully grown and is harvested by a custom hoe, replant the crop
        // return if the crop isn't fully grown or if the crop is harvested by a non-custom hoe
        if(age.getAge() != age.getMaximumAge()) {
            ItemStack handItem = player.getInventory().getItemInMainHand();

            if(!manager.isCustomHoe(handItem)) {
                return;
            }
            manager.replant(event.getBlock(), event.getBlockState().getType(), age, event.getItems());
            return;
        }


        for (int i = 0; i < event.getItems().size(); i++) {
            // Chance to get a super crop
            int chance = 300;
            int rand = (int) (Math.random() * chance) + 1;
            int jackpotNum = 256;

            boolean jackpot = true;//(rand == jackpotNum); // If the random number is the jackpot, set jackpot to true

            // Item replanting whether the player got the jackpot for a super crop
            ItemStack handItem = player.getInventory().getItemInMainHand(); // Get the item in the player's main hand (usually the hoe)
            if(manager.isCustomHoeOne(handItem)) {
                for(Item droppedItem : event.getItems()) {
                    droppedItem.remove(); //Remove dropped items from the world that were harvested
                    if(manager.isCropSeed(droppedItem.getItemStack().getType())) {
                        //If the item is a crop that uses seeds, drop 1 less seed
                        droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
                    }
                    //Give the player the dropped items if they haven't got the jackpot
                    if (!jackpot) {
                        player.getInventory().addItem(droppedItem.getItemStack());
                    }
                }
                //Set the age of the crop to 0 as it has been replanted
                Ageable ageable = (Ageable) event.getBlockState().getBlockData();
                ageable.setAge(0);

                //Replant the crop
                manager.replantLater(event.getBlock(), event.getBlockState().getType(), ageable);
            }

            // If the player is using a custom hoe, remove the dropped items and replant the crop
            // Same comments as previous, but without the seed dropping
            else if(manager.isCustomHoeTwo(handItem)) {
                for (Item droppedItem : event.getItems()) {
                    droppedItem.remove();
                }

                Ageable ageable = (Ageable) event.getBlockState().getBlockData();
                ageable.setAge(0);

                manager.replantLater(event.getBlock(), event.getBlockState().getType(), ageable);
            }

            if(!jackpot) return; // If the random number is not the jackpot, return

            // If the player got the jackpot...
            player.sendMessage(manager.cropBlockMessage(event.getBlockState().getType()));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + manager.cropBlockLore(event.getBlockState().getType()));
            // Create a super crop
            ItemStack superCrop = manager.makeSuperCrop(manager.cropBlockName(event.getBlockState().getType()), manager.cropBlockToItem(event.getBlockState().getType()), lore, 1, 1);

            /*if (manager.hasRoom(player, superCrop)) {
                // Give the player the super crop if they are using a custom hoe
                if (manager.isCustomHoe(handItem)) {
                    manager.giveSuperCrop(player, superCrop);
                    return;
                }
                // Drop the super crop if the player is not using a custom hoe
                else if (!manager.isCustomHoe(handItem)) {
                    Item superCropItem = event.getItems().get(0).getWorld().dropItem(event.getItems().get(0).getLocation(), superCrop);
                    superCropItem.setGlowing(true);
                    return;
                }
            }
            else {
                manager.fullInventoryAlert(player);
                Item superCropItem = event.getItems().get(0).getWorld().dropItem(event.getItems().get(0).getLocation(), superCrop);
                superCropItem.setGlowing(true);
                return;
            }*/
            if (manager.isCustomHoe(handItem)) {
                manager.giveSuperCrop(player, superCrop);
                return;
            }
            // Drop the super crop if the player is not using a custom hoe
            else if (!manager.isCustomHoe(handItem)) {
                Item superCropItem = event.getItems().get(0).getWorld().dropItem(event.getItems().get(0).getLocation(), superCrop);
                superCropItem.setGlowing(true);
                return;
            }
        }
    }
}
