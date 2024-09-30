package me.glasscrab.plentiful_harvest.Listeners.OnConsumeEvents;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class OnWarpedPotionConsumeEvent implements Listener {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    @EventHandler
    public void onPotionConsume(PlayerItemConsumeEvent e){
        if(!e.getItem().getType().equals(Material.POTION)) return;
        if(!e.getItem().hasItemMeta()) return;
        if(e.getItem().getItemMeta() == null) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 1) return;

        if(!e.getPlayer().getWorld().isPiglinSafe()){
            e.setCancelled(true);
            e.getPlayer().sendActionBar(miniMessage.deserialize("<red>You can only drink this in the Nether!</red>"));
        }
    }

}
