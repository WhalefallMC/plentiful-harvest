package me.glasscrab.plentiful_harvest.Listeners;

import org.bukkit.entity.Breedable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AnimalFeedEvent implements Listener {
    
    @EventHandler
    public void feedAnimal(PlayerInteractEntityEvent e) {
        if(!(e.getRightClicked() instanceof Breedable)) return;
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();

        if((hand != null && hand.getItemMeta() != null && hand.getItemMeta().hasCustomModelData() && hand.getItemMeta().getCustomModelData() == 1) || offhand != null && offhand.getItemMeta() != null && offhand.getItemMeta().hasCustomModelData() && offhand.getItemMeta().getCustomModelData() == 1) {
            e.setCancelled(true);
        }
    }

}
