package me.glasscrab.plentiful_harvest.Listeners.OnConsumeEvents;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class OnWholeBreadConsumeEvent implements Listener {


    @EventHandler
    public void onWholeBreadConsume(PlayerItemConsumeEvent e){
        if(!e.getItem().getType().equals(Material.BREAD)) return;
        if(!e.getItem().hasItemMeta()) return;
        if(e.getItem().getItemMeta() == null) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 1) return;

        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setSaturation(20);
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW+"You feel nourished."));
    }
}
