package me.glasscrab.plentiful_harvest.Recipes;

import me.glasscrab.plentiful_harvest.PlentifulHarvest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CustomRecipes {
    private final Plugin plugin;
    public CustomRecipes(PlentifulHarvest plugin){
        this.plugin = plugin;
    }

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

    public ShapedRecipe farmersBootsShapedRecipe(){
        ItemStack farmersBoots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta farmersBootsMeta = farmersBoots.getItemMeta();
        var miniMessage = MiniMessage.miniMessage();
        ((LeatherArmorMeta) farmersBootsMeta).setColor(Color.fromRGB(245, 113, 37));
        farmersBootsMeta.itemName(miniMessage.deserialize("<gold>Farmer's Boots</gold>"));
        farmersBootsMeta.lore(List.of(miniMessage.deserialize("<gray>Wear these and you'll never have</gray>"), 
                                    miniMessage.deserialize("<gray>to worry about trampling crops again!</gray>")));
        farmersBootsMeta.setCustomModelData(1);
        farmersBootsMeta.setUnbreakable(true);
        farmersBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(NamespacedKey.minecraft("generic.armor"), 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET));
        farmersBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        farmersBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        farmersBootsMeta.addItemFlags(ItemFlag.HIDE_DYE);

        farmersBoots.setItemMeta(farmersBootsMeta);

        NamespacedKey farmersBootsKey = new NamespacedKey(plugin, "farmers_boots");
        ShapedRecipe farmersBootsRecipe = new ShapedRecipe(farmersBootsKey, farmersBoots);

        farmersBootsRecipe.shape("OOO","ONO","OOO");
        farmersBootsRecipe.setIngredient('O', Material.ORANGE_WOOL);
        farmersBootsRecipe.setIngredient('N', Material.NETHERITE_BOOTS);
        return farmersBootsRecipe;
    }

    public ShapedRecipe farmersHoeShapedRecipe(){
        ItemStack farmersHoe = new ItemStack(Material.WOODEN_HOE);
        ItemMeta farmersHoeMeta = farmersHoe.getItemMeta();
        var miniMessage = MiniMessage.miniMessage();
        farmersHoeMeta.itemName(miniMessage.deserialize("<gold>Farmer's Hoe</gold>"));
        farmersHoeMeta.lore(List.of(miniMessage.deserialize("<gray>New technology allows for automatic</gray>"),
                                    miniMessage.deserialize("<gray>replanting of crops.</gray>")));
        farmersHoeMeta.setCustomModelData(2767);
        farmersHoeMeta.setUnbreakable(true);
        farmersHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        farmersHoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        farmersHoe.setItemMeta(farmersHoeMeta);

        NamespacedKey farmersHoeKey = new NamespacedKey(plugin, "farmers_hoe");
        ShapedRecipe farmersHoeRecipe = new ShapedRecipe(farmersHoeKey, farmersHoe);

        farmersHoeRecipe.shape(" C ","CHC"," C ");
        farmersHoeRecipe.setIngredient('C', new RecipeChoice.ExactChoice(List.of(makeSuperCrop(miniMessage.deserialize("<yellow>Whole Wheat</yellow>"),Material.WHEAT,List.of(miniMessage.deserialize("<gray>A whole lot better than the other kind.</gray>")),1,1),
                                                                                makeSuperCrop(miniMessage.deserialize("<gold>Hyper Carrot</gold>"),Material.CARROT,List.of(miniMessage.deserialize("<gray>A single one could feed 100 horses.</gray>")),1,1),
                                                                                makeSuperCrop(miniMessage.deserialize("<green>Medicinal Potato</green>"),Material.POTATO,List.of(miniMessage.deserialize("<gray>Opposite to the poisonous potato, and much rarer.</gray>")),1,1),
                                                                                makeSuperCrop(miniMessage.deserialize("<light_purple>Mystic Beetroot</light_purple>"),Material.BEETROOT,List.of(miniMessage.deserialize("<gray>A beetroot so old it's been infused with magic.</gray>")),1,1),
                                                                                makeSuperCrop(miniMessage.deserialize("<dark_aqua>Warped Nether Wart</dark_aqua>"),Material.NETHER_WART,List.of(miniMessage.deserialize("<gray>Like a four leaf clover, found very rarely.</gray>")),1,1))));

        farmersHoeRecipe.setIngredient('H', Material.NETHERITE_HOE);
        return farmersHoeRecipe;
    }

    public StonecuttingRecipe wholeFlourStoneCuttingRecipe(){
        ItemStack wholeFlour = new ItemStack(Material.SUGAR);
        ItemMeta wholeFlourMeta = wholeFlour.getItemMeta();
        var miniMessage = MiniMessage.miniMessage();
        wholeFlourMeta.itemName(miniMessage.deserialize("<white>Whole Flour</white>"));
        wholeFlourMeta.lore(List.of(miniMessage.deserialize("<gray>100% whole grain.</gray>")));
        wholeFlourMeta.setCustomModelData(1);

        wholeFlour.setItemMeta(wholeFlourMeta);

        NamespacedKey wholeFlourKey = new NamespacedKey(plugin, "whole_flour");
        StonecuttingRecipe wholeFlourRecipe = new StonecuttingRecipe(wholeFlourKey,wholeFlour,new RecipeChoice.ExactChoice(makeSuperCrop(miniMessage.deserialize("<yellow>Whole Wheat</yellow>"),Material.WHEAT,List.of(miniMessage.deserialize("<gray>A whole lot better than the other kind.</gray>")),1,1)));
        return wholeFlourRecipe;
    }

    public SmokingRecipe wholeBreadSmokingRecipe(){
        ItemStack wholeFlour = new ItemStack(Material.SUGAR);
        ItemMeta wholeFlourMeta = wholeFlour.getItemMeta();
        var miniMessage = MiniMessage.miniMessage();
        wholeFlourMeta.itemName(miniMessage.deserialize("<white>Whole Flour</white>"));
        wholeFlourMeta.lore(List.of(miniMessage.deserialize("<gray>100% whole grain.</gray>")));
        wholeFlourMeta.setCustomModelData(1);

        wholeFlour.setItemMeta(wholeFlourMeta);

        ItemStack wholeBread = new ItemStack(Material.BREAD);
        ItemMeta wholeBreadMeta = wholeBread.getItemMeta();
        wholeBreadMeta.itemName(miniMessage.deserialize("<yellow>Whole Bread</yellow>"));
        wholeBreadMeta.lore(List.of(miniMessage.deserialize("<gray>Refills your whole hunger bar.</gray>")));
        wholeBreadMeta.setCustomModelData(1);

        wholeBread.setItemMeta(wholeBreadMeta);

        NamespacedKey wholeBreadKey = new NamespacedKey(plugin, "whole_bread");
        SmokingRecipe wholeBreadRecipe = new SmokingRecipe(wholeBreadKey, wholeBread, new RecipeChoice.ExactChoice(wholeFlour), 10f, 400);
        return wholeBreadRecipe;
    }
}