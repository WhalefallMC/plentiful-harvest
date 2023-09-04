package me.glasscrab.plentiful_harvest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmlandTrampleEvent implements Listener {

    @EventHandler
    public void trampleFarmland(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.PHYSICAL)) return;
        if(!e.getClickedBlock().getType().equals(Material.FARMLAND)) return;
        if(e.getPlayer().getEquipment() == null) return;
        if(e.getPlayer().getEquipment().getBoots() == null) return;
        if(!e.getPlayer().getEquipment().getBoots().getType().equals(Material.LEATHER_BOOTS)) return;
        if(!e.getPlayer().getEquipment().getBoots().hasItemMeta()) return;
        if(!e.getPlayer().getEquipment().getBoots().getItemMeta().hasCustomModelData()) return;
        if(e.getPlayer().getEquipment().getBoots().getItemMeta().getCustomModelData() != 1) return;

        e.setCancelled(true);

    }
}