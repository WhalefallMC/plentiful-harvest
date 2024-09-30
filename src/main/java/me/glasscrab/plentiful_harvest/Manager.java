package me.glasscrab.plentiful_harvest;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Manager {
    private static Manager manager;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final Set<Material> seedItems = new HashSet<>();
    private static final Set<Material> cropBlocks = new HashSet<>();

    public Manager() {
        manager = this;

        seedItems.add(Material.WHEAT_SEEDS);
        seedItems.add(Material.BEETROOT_SEEDS);
        seedItems.add(Material.CARROT);
        seedItems.add(Material.NETHER_WART);
        seedItems.add(Material.POTATO);

        cropBlocks.add(Material.BEETROOTS);
        cropBlocks.add(Material.CARROTS);
        cropBlocks.add(Material.NETHER_WART);
        cropBlocks.add(Material.POTATOES);
        cropBlocks.add(Material.WHEAT);
    }
    /*
     * Create a super crop item
     * @param name - name of the super crop
     * @param material - material of the super crop
     * @param lore - lore of the super crop
     * @param customModelData - custom model data of the super crop
     * @param amount - amount of the super crop
     * @returns ItemStack of the new super crop
     */
    public ItemStack makeSuperCrop(Component name, Material material, List<Component> lore, int customModelData, int amount) {
        ItemStack superCropItem = new ItemStack(material, amount);
        ItemMeta superCropItemMeta = superCropItem.getItemMeta();

        if(superCropItemMeta == null) {
            return null;
        }

        superCropItemMeta.itemName(name);
        superCropItemMeta.lore(lore);
        superCropItemMeta.setCustomModelData(customModelData);
        superCropItem.setItemMeta(superCropItemMeta);

        return superCropItem;
    }

    /*
     * Checks if the player's inventory is full
     * @param player - the player targeted
     * @returns boolean dictating if the inventory is full
     */
    public boolean isFull(Player player) {
        //Returns -1 if the inventory is full
        return player.getInventory().firstEmpty() == -1;
    }

    /*
     * Adds an item to the player's inventory
     * @param player - the player targeted
     * @param item - the item to be added
     * @returns the remaining item if the inventory is full
     */
    public ItemStack addInventory(Player player, ItemStack item) {
        Map<Integer, ItemStack> remaining = player.getInventory().addItem(item);
        return remaining.isEmpty() ? null : remaining.get(0);
    }

    /*
     * Sends an on-screen message to the player informing them that their inventory is full and they cannot collect supercrops
     * @param player - the player targeted
     * @param message - the message to be sent
     * @returns void
     */
    public void fullInventoryAlert(Player player){
        player.sendActionBar(miniMessage.deserialize("<red>YOUR INVENTORY IS FULL! YOU CANNOT COLLECT SUPER CROPS!</red>"));
        player.playSound(player, Sound.BLOCK_BELL_USE, 0.6f, 1);
    }

    /*
     * Gives the player a super crop
     * @param player - the player targeted
     * @param superCrop - the super crop to be given
     * @returns void
     */
    public void giveSuperCrop(Player player, ItemStack superCrop) {
        ItemStack item = addInventory(player, superCrop);
        if (item == null) return;
        if (isFull(player)) {
            fullInventoryAlert(player);
            Item superCropItem = player.getWorld().dropItem(player.getLocation(), item);//Drops to world
            superCropItem.setGlowing(true);
        }
    }

    /*
     * Checks if the item is an old hoe
     * @param item - the item to be checked
     * @returns boolean dictating if the item is an old hoe
     */
    public boolean isOldHoe(ItemStack item){
        if(!item.getType().equals(Material.WOODEN_HOE)) return false;
        if(!item.hasItemMeta()) return false;
        if(item.getItemMeta() == null) return false;
        if(!item.getItemMeta().hasCustomModelData()) return false;
        return item.getItemMeta().getCustomModelData() < 106 && item.getItemMeta().getCustomModelData() > 0;
    }

    /*
     * Checks if the item is a crop seed
     * @param cropSeedType - the type of the crop seed
     * @returns boolean dictating if the item is a crop seed
     */
    public boolean isCropSeed(Material cropSeedType){
        return seedItems.contains(cropSeedType);
    }

    /*
     * Checks if the item is a crop block
     * @param cropBlockType - the type of the crop block
     * @returns boolean dictating if the item is a crop block
     */
    public boolean isCropBlock(Material cropBlockType){
        return cropBlocks.contains(cropBlockType);
    }

    /*
     * Converts a crop block to an item
     * @param cropBlockType - the type of the crop block
     * @returns the item of the crop block
     */
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

    /*
     * Converts a crop block to a lore
     * @param cropBlockType - the type of the crop block
     * @returns the lore of the crop block
     */
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

    /*
     * Converts a crop block to a name
     * @param cropBlockType - the type of the crop block
     * @returns the name of the crop block
     */
    public Component cropBlockName(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> miniMessage.deserialize("<yellow>Whole Wheat</yellow>");
            case CARROTS -> miniMessage.deserialize("<gold>Hyper Carrot</gold>");
            case POTATOES -> miniMessage.deserialize("<green>Medicinal Potato</green>");
            case BEETROOTS -> miniMessage.deserialize("<light_purple>Mystic Beetroot</light_purple>");
            case NETHER_WART -> miniMessage.deserialize("<dark_aqua>Warped Nether Wart</dark_aqua>");
            default -> miniMessage.deserialize("<gray>Error Crop</gray>");
        };
    }

    /*
     * Converts a crop block to a message
     * @param cropBlockType - the type of the crop block
     * @returns the message of the crop block
     */
    public Component cropBlockMessage(Material cropBlockType){
        return switch (cropBlockType) {
            case WHEAT -> miniMessage.deserialize("<yellow>You harvested a bundle of Whole Wheat!</yellow>");
            case CARROTS -> miniMessage.deserialize("<gold>You uprooted a Hyper Carrot!</gold>");
            case POTATOES -> miniMessage.deserialize("<green>You dug up a Medicinal Potato!</green>");
            case BEETROOTS -> miniMessage.deserialize("<light_purple>You felt the aura of a Mystic Beetroot!</light_purple>");
            case NETHER_WART -> miniMessage.deserialize("<dark_aqua>You uncovered a Warped Nether Wart!</dark_aqua>");
            default -> miniMessage.deserialize("<gray>Error Crop Message</gray>");
        };
    }

    /*
     * Checks if the item is a custom hoe
     * @param item - the item to be checked
     * @returns boolean dictating if the item is a custom hoe
     */
    public boolean isCustomHoe(ItemStack item) {
        return item.getType().equals(Material.WOODEN_HOE) &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                (item.getItemMeta().getCustomModelData() == 2767 ||
                item.getItemMeta().getCustomModelData() == 2768);
    }

    /*
     * Replants a crop
     * @param block - the block to be replanted
     * @param cropType - the type of the crop
     * @param age - the age of the crop
     * @param droppedItems - the items dropped from the crop
     * @returns void
     */
    public void replant(Block block, Material cropType, BlockData age, List<Item> droppedItems) {
        block.setType(cropType);
        block.setBlockData(age);

        for(Item droppedItem : droppedItems){
            droppedItem.remove();
        }
    }

    /*
     * Checks if the item is a custom hoe
     * Checks custom model data 2767
     * @param hoe - the hoe to be checked
     * @returns true if the hoe is a custom hoe
     */
    public boolean isCustomHoeOne(ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().hasCustomModelData() &&
                hoe.getItemMeta().getCustomModelData() == 2767;
    }

    /*
     * Checks if the item is a custom hoe
     * Checks custom model data 2768
     * @param hoe - the hoe to be checked
     * @returns true if the hoe is a custom hoe
     */
    public boolean isCustomHoeTwo(ItemStack hoe) {
        return hoe.getType().equals(Material.WOODEN_HOE) &&
                hoe.getItemMeta() != null &&
                hoe.getItemMeta().hasCustomModelData() &&
                hoe.getItemMeta().getCustomModelData() == 2768;
    }

    /*
     * Replants a crop later
     * @param block - the block to be replanted
     * @param cropType - the type of the crop
     * @param age - the age of the crop
     * @returns void
     */
    public void replantLater(Block block, Material cropType, BlockData age) {
        new BukkitRunnable() {
            public void run() {
                block.setType(cropType);
                block.setBlockData(age);
            }
        }.runTaskLater(PlentifulHarvest.INSTANCE, 1);
    }

    /*
     * Gets the manager
     * @returns the manager
     */
    public static Manager getManager() {
        return manager;
    }
}
