package me.glasscrab.plentiful_harvest.Listeners.Brewing;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;

public class WarpedNetherWartBrewEvent implements Listener {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    //CREATE EVENT FOR DRINKING POTION SO YOU CAN ONLY DRINK THIS IN THE NETHER

    @EventHandler
    public void onWarpedNetherWartBrew(BrewEvent e){
        if(e.getContents().getIngredient() == null) return;
        if(!e.getContents().getIngredient().hasItemMeta()) return;
        if(!e.getContents().getIngredient().getItemMeta().hasCustomModelData()) return;
        if(e.getContents().getIngredient().getItemMeta().getCustomModelData() != 1) return;

        ItemStack hastePotion = new ItemStack(Material.POTION);
        PotionMeta hastePotionMeta = (PotionMeta) hastePotion.getItemMeta();
        hastePotionMeta.setColor(Color.fromRGB(0,153,153));
        hastePotionMeta.itemName(miniMessage.deserialize("<white>Warped Potion</white>"));
        hastePotionMeta.lore(List.of(miniMessage.deserialize("<blue>Netherborn (2:00)</blue>")));
        hastePotionMeta.setCustomModelData(1);
        hastePotionMeta.addCustomEffect(PotionEffectType.HASTE.createEffect(20*120,1),false);
        hastePotionMeta.addCustomEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(20*120,0),false);
        hastePotionMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        hastePotion.setItemMeta(hastePotionMeta);
        for(ItemStack i: e.getResults()){
            i.setItemMeta(hastePotion.getItemMeta());
        }
    }

}
