package me.glasscrab.plentiful_harvest.Listeners;

import me.glasscrab.plentiful_harvest.Manager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class DisableNonPlantableCropsEvent implements Listener {

    private final Manager manager;

    public DisableNonPlantableCropsEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void disableNonPlantableCrops(BlockPlaceEvent e){
        if(e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if(!e.getItemInHand().hasItemMeta()) return;
        if(e.getItemInHand().getItemMeta() == null) return;
        if(!e.getItemInHand().getItemMeta().hasCustomModelData()) return;
        if(!manager.isCropSeed(e.getItemInHand().getType())) return;
        if(e.getItemInHand().getItemMeta().getCustomModelData() == 2 || e.getItemInHand().getItemMeta().getCustomModelData() == 1){
            e.setCancelled(true);
        }


    }
}
