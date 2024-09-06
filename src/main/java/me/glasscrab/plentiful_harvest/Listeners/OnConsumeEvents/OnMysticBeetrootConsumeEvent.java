package me.glasscrab.plentiful_harvest.Listeners.OnConsumeEvents;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class OnMysticBeetrootConsumeEvent implements Listener {

    @EventHandler
    public void onMysticBeetrootConsume(PlayerItemConsumeEvent e){
        if(!e.getItem().getType().equals(Material.BEETROOT)) return;
        if(!e.getItem().hasItemMeta()) return;
        if(e.getItem().getItemMeta() == null) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 1) return;

        for(ItemStack i : e.getPlayer().getInventory().getContents()){
            if(i == null) continue;
            if(!i.hasItemMeta()) continue;
            if(i.getItemMeta() instanceof Damageable){
                Damageable damageable = (Damageable)i.getItemMeta();
                damageable.setDamage(0);
                i.setItemMeta(damageable);
            }
        }
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(ChatColor.LIGHT_PURPLE+"The beetroot's aura envelops your tools.."));
    }
}
