package me.glasscrab.plentiful_harvest.Listeners.Brewing;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class WarpedNetherWartBrewEvent implements Listener {

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
        hastePotionMeta.setDisplayName(ChatColor.WHITE+"Warped Potion");
        hastePotionMeta.setLore(List.of(ChatColor.BLUE+"Netherborn (2:00)"));
        hastePotionMeta.setCustomModelData(1);
        hastePotionMeta.addCustomEffect(PotionEffectType.FAST_DIGGING.createEffect(20*120,1),false);
        hastePotionMeta.addCustomEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(20*120,0),false);
        hastePotionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        hastePotion.setItemMeta(hastePotionMeta);
        for(ItemStack i: e.getResults()){
            i.setItemMeta(hastePotion.getItemMeta());
        }
    }

}
