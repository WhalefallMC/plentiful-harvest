package me.glasscrab.plentiful_harvest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Manager {
    private static Manager manager;
    public Manager() {
        manager = this;
    }

    public ItemStack makeSuperCrop(String name, Material material, List<String> lore, int customModelData){
        ItemStack superCropItem = new ItemStack(material,1);
        ItemMeta superCropItemMeta = superCropItem.getItemMeta();

        superCropItemMeta.setDisplayName(name);
        superCropItemMeta.setLore(lore);
        superCropItemMeta.setCustomModelData(customModelData);
        superCropItem.setItemMeta(superCropItemMeta);

        return superCropItem;
    }

    public boolean isFull(Player player, ItemStack item){
        for (ItemStack checkItem : player.getInventory().getStorageContents()){
            if(checkItem.getItemMeta() == null) return false;
            if(checkItem.getItemMeta().equals(item.getItemMeta()) && checkItem.getAmount() < 62){
                return false;
            }
        }
        return true;
    }

    public static Manager getManager() {
        return manager;
    }
}
