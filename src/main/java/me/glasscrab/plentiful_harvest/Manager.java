package me.glasscrab.plentiful_harvest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public boolean isFull(Player player) {
        //Returns -1 if the inventory is full
        return player.getInventory().firstEmpty() == -1;
    }

    public void giveSuperCrop(Player player, ItemStack superCrop) {
        if (isFull(player)) {
            player.getWorld().dropItem(player.getLocation(), superCrop);//Drops to world
            return;
        }
        player.getInventory().addItem(superCrop); // Gives item to player inventory
    }

    public boolean isOldHoe(ItemStack item){
        if(!item.getType().equals(Material.WOODEN_HOE)) return false;
        if(!item.hasItemMeta()) return false;
        if(item.getItemMeta() == null) return false;
        if(!item.getItemMeta().hasCustomModelData()) return false;
        return item.getItemMeta().getCustomModelData() < 106 && item.getItemMeta().getCustomModelData() > 0;
    }

    public boolean isCropSeed(Material cropSeedType){
        Set<Material> seedItems = new HashSet<>();
        seedItems.add(Material.WHEAT_SEEDS);
        seedItems.add(Material.BEETROOT_SEEDS);
        seedItems.add(Material.CARROT);
        seedItems.add(Material.NETHER_WART);
        seedItems.add(Material.POTATO);
        return seedItems.contains(cropSeedType);
    }

    public boolean isCropBlock(Material cropBlockType){
        Set<Material> cropBlocks = new HashSet<>();
        cropBlocks.add(Material.BEETROOTS);
        cropBlocks.add(Material.CARROTS);
        cropBlocks.add(Material.NETHER_WART);
        cropBlocks.add(Material.POTATOES);
        cropBlocks.add(Material.WHEAT);
        return cropBlocks.contains(cropBlockType);
    }

    public Material cropBlockToItem(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> Material.WHEAT;
            case CARROTS -> Material.CARROT;
            case POTATOES -> Material.POTATO;
            case BEETROOTS -> Material.BEETROOT;
            case NETHER_WART -> Material.NETHER_WART;
            default -> Material.STONE;
        };
    }

    public String cropBlockLore(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> "A whole lot better than the other kind.";
            case CARROTS -> "A single one could feed 100 horses.";
            case POTATOES -> "Opposite to the poisonous potato, and much rarer.";
            case BEETROOTS -> "A beetroot so old it's been infused with magic.";
            case NETHER_WART -> "Like a four leaf clover, found very rarely.";
            default -> "Dm me if u got this item";
        };
    }

    public String cropBlockName(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> ChatColor.YELLOW+"Whole Wheat";
            case CARROTS -> ChatColor.GOLD+"Hyper Carrot";
            case POTATOES -> ChatColor.GREEN+"Medicinal Potato";
            case BEETROOTS -> ChatColor.LIGHT_PURPLE+"Mystic Beetroot";
            case NETHER_WART -> ChatColor.DARK_AQUA+"Warped Nether Wart";
            default -> ChatColor.GRAY+"Error Crop";
        };
    }

    public String cropBlockMessage(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> ChatColor.YELLOW+"You harvested a bundle of Whole Wheat!";
            case CARROTS -> ChatColor.GOLD+"You uprooted a Hyper Carrot!";
            case POTATOES -> ChatColor.GREEN+"You dug up a Medicinal Potato!";
            case BEETROOTS -> ChatColor.LIGHT_PURPLE+"You felt the aura of a Mystic Beetroot!";
            case NETHER_WART -> ChatColor.DARK_AQUA+"You uncovered a Warped Nether Wart!";
            default -> ChatColor.GRAY+"Error Crop Message";
        };
    }

    public boolean isCustomHoe(ItemStack item) {

        return item.getType().equals(Material.WOODEN_HOE) &&
                item.getItemMeta() != null &&
                (item.getItemMeta().getCustomModelData() == 2767 ||
                item.getItemMeta().getCustomModelData() == 2768);
    }

    public void replant(Block block, Material cropType, BlockData age, List<Item> droppedItems) {
        block.setType(cropType);
        block.setBlockData(age);

        for(Item droppedItem : droppedItems){
            droppedItem.remove();
        }
    }

    public boolean isCustomHoeOne(ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().getCustomModelData() == 2767;
    }

    public boolean isCustomHoeTwo(ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().getCustomModelData() == 2768;
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
