package me.glasscrab.plentiful_harvest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Manager {
    private static Manager manager;

    public Manager() {
        manager = this;
    }
    public ItemStack makeSuperCrop(String name, Material material, List<String> lore, int customModelData, int amount) {
        ItemStack superCropItem = new ItemStack(material, amount);
        ItemMeta superCropItemMeta = superCropItem.getItemMeta();

        if(superCropItemMeta == null) {
            return null;
        }

        superCropItemMeta.setDisplayName(name);
        superCropItemMeta.setLore(lore);
        superCropItemMeta.setCustomModelData(customModelData);
        superCropItem.setItemMeta(superCropItemMeta);

        return superCropItem;
    }

    public boolean isFull(Player player, ItemStack item) {
        for (ItemStack checkItem : player.getInventory().getStorageContents()) {
            if(checkItem.getItemMeta() == null) return false;
            if(checkItem.getItemMeta().equals(item.getItemMeta()) && checkItem.getAmount() < 62) return false;
        }
        
        return true;
    }

    public void giveSuperCrop(Player player, ItemStack superCrop, BlockDropItemEvent event) {
        player.getInventory().addItem(superCrop);

        if(player.getInventory().firstEmpty() != -1) return;
        if(this.isFull(player, superCrop)) return;

        Item superCropItem = event.getItems().get(0).getWorld().dropItem(event.getItems().get(0).getLocation(), superCrop);
        superCropItem.setGlowing(true);
    }

    public void giveDroppedItems(Player player, List<Item> droppedItems) {
        for(Item droppedItem : droppedItems) {
            droppedItem.remove();
            droppedItem.getItemStack().setAmount(droppedItem.getItemStack().getAmount()-1);
            player.getInventory().addItem(droppedItem.getItemStack());
        }
    }

    private int getFirstCustomData(Material cropType) {
        return switch (cropType) {
            case WHEAT -> 1;
            case CARROTS -> 2;
            case POTATOES -> 3;
            case BEETROOTS -> 4;
            case NETHER_WART -> 5;
            default -> 0;
        };
    }

    private int getSecondCustomModelData(Material cropType) {
        return switch (cropType) {
            case WHEAT -> 101;
            case CARROTS -> 102;
            case POTATOES -> 103;
            case BEETROOTS -> 104;
            case NETHER_WART -> 105;
            default -> 0;
        };
    }

    public boolean isCustomHoe(ItemStack item, Material cropType) {
        int customData1 = getFirstCustomData(cropType);
        int customData2 = getSecondCustomModelData(cropType);

        return item.getType().equals(Material.WOODEN_HOE) &&
                item.getItemMeta() != null &&
                (item.getItemMeta().getCustomModelData() == customData1 ||
                        item.getItemMeta().getCustomModelData() == customData2
                );
    }

    public void replant(Block block, Material cropType, BlockData age, List<Item> droppedItems) {
        block.setType(cropType);
        block.setBlockData(age);

        for(Item droppedItem : droppedItems){
            droppedItem.remove();
        }
    }

    public boolean isCustomHoeOne(Material cropType, ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().getCustomModelData() == getFirstCustomData(cropType);
    }

    public boolean isCustomHoeTwo(Material cropType, ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().getCustomModelData() == getSecondCustomModelData(cropType);
    }

    public void replantLater(Block block, Material cropType, BlockData age) {
        new BukkitRunnable() {
            public void run() {
                block.setType(cropType);
                block.setBlockData(age);
            }
        }.runTaskLater(PlentifulHarvest.INSTANCE, 1);
    }

    public static Manager getManager() {
        return manager;
    }
}
