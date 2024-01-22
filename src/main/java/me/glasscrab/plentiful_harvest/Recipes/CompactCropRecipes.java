package me.glasscrab.plentiful_harvest.Recipes;

import me.glasscrab.plentiful_harvest.PlentifulHarvest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CompactCropRecipes {
    private final Plugin plugin;
    public CompactCropRecipes(PlentifulHarvest plugin){
        this.plugin = plugin;
    }

    public ShapedRecipe compactWheatSeedsShapedRecipe(){
        ItemStack seedsItem = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta seedsMeta = seedsItem.getItemMeta();
        seedsMeta.setDisplayName(ChatColor.WHITE+"Pile of Wheat Seeds");
        seedsMeta.setCustomModelData(2);
        seedsItem.setItemMeta(seedsMeta);

        NamespacedKey seedsKey = new NamespacedKey(plugin, "compact_wheat_seeds");
        ShapedRecipe compactWheatSeedsRecipe = new ShapedRecipe(seedsKey,seedsItem);

        compactWheatSeedsRecipe.shape("SSS","SSS","SSS");

        compactWheatSeedsRecipe.setIngredient('S',Material.WHEAT_SEEDS);
        return compactWheatSeedsRecipe;
    }

    public ShapelessRecipe uncompactWheatSeedsShapelessRecipe(){
        ItemStack seedsItem = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta seedsMeta = seedsItem.getItemMeta();
        seedsMeta.setDisplayName(ChatColor.WHITE+"Pile of Wheat Seeds");
        seedsMeta.setCustomModelData(2);
        seedsItem.setItemMeta(seedsMeta);

        NamespacedKey seedsKey = new NamespacedKey(plugin, "uncompact_wheat_seeds");
        ShapelessRecipe uncompactWheatSeedsRecipe = new ShapelessRecipe(seedsKey,new ItemStack(Material.WHEAT_SEEDS,9));

        uncompactWheatSeedsRecipe.addIngredient(new RecipeChoice.ExactChoice(seedsItem));
        return uncompactWheatSeedsRecipe;
    }

    public ShapedRecipe compactCarrotShapedRecipe(){
        ItemStack carrotItem = new ItemStack(Material.CARROT);
        ItemMeta carrotMeta = carrotItem.getItemMeta();
        carrotMeta.setDisplayName(ChatColor.WHITE+"Pile of Carrots");
        carrotMeta.setCustomModelData(2);
        carrotItem.setItemMeta(carrotMeta);

        NamespacedKey carrotKey = new NamespacedKey(plugin, "compact_carrot");
        ShapedRecipe compactCarrotRecipe = new ShapedRecipe(carrotKey,carrotItem);

        compactCarrotRecipe.shape("CCC","CCC","CCC");

        compactCarrotRecipe.setIngredient('C',Material.CARROT);
        return compactCarrotRecipe;
    }

    public ShapelessRecipe uncompactCarrotShapelessRecipe(){
        ItemStack carrotItem = new ItemStack(Material.CARROT);
        ItemMeta carrotMeta = carrotItem.getItemMeta();
        carrotMeta.setDisplayName(ChatColor.WHITE+"Pile of Carrots");
        carrotMeta.setCustomModelData(2);
        carrotItem.setItemMeta(carrotMeta);

        NamespacedKey carrotKey = new NamespacedKey(plugin, "uncompact_carrot");
        ShapelessRecipe uncompactCarrotRecipe = new ShapelessRecipe(carrotKey,new ItemStack(Material.CARROT,9));

        uncompactCarrotRecipe.addIngredient(new RecipeChoice.ExactChoice(carrotItem));
        return uncompactCarrotRecipe;
    }

    public ShapedRecipe compactPotatoShapedRecipe(){
        ItemStack potatoItem = new ItemStack(Material.POTATO);
        ItemMeta potatoMeta = potatoItem.getItemMeta();
        potatoMeta.setDisplayName(ChatColor.WHITE+"Pile of Potatoes");
        potatoMeta.setCustomModelData(2);
        potatoItem.setItemMeta(potatoMeta);

        NamespacedKey potatoKey = new NamespacedKey(plugin, "compact_potato");
        ShapedRecipe compactPotatoRecipe = new ShapedRecipe(potatoKey,potatoItem);

        compactPotatoRecipe.shape("PPP","PPP","PPP");

        compactPotatoRecipe.setIngredient('P',Material.POTATO);
        return compactPotatoRecipe;
    }

    public ShapelessRecipe uncompactPotatoShapelessRecipe(){
        ItemStack potatoItem = new ItemStack(Material.POTATO);
        ItemMeta potatoMeta = potatoItem.getItemMeta();
        potatoMeta.setDisplayName(ChatColor.WHITE+"Pile of Potatoes");
        potatoMeta.setCustomModelData(2);
        potatoItem.setItemMeta(potatoMeta);

        NamespacedKey potatoKey = new NamespacedKey(plugin, "uncompact_potato");
        ShapelessRecipe uncompactPotatoRecipe = new ShapelessRecipe(potatoKey,new ItemStack(Material.POTATO,9));

        uncompactPotatoRecipe.addIngredient(new RecipeChoice.ExactChoice(potatoItem));
        return uncompactPotatoRecipe;
    }

    public ShapedRecipe compactBeetrootSeedsShapedRecipe(){
        ItemStack beetrootSeedsItem = new ItemStack(Material.BEETROOT_SEEDS);
        ItemMeta beetrootSeedsMeta = beetrootSeedsItem.getItemMeta();
        beetrootSeedsMeta.setDisplayName(ChatColor.WHITE+"Pile of Beetroot Seeds");
        beetrootSeedsMeta.setCustomModelData(2);
        beetrootSeedsItem.setItemMeta(beetrootSeedsMeta);

        NamespacedKey beetrootSeedsKey = new NamespacedKey(plugin, "compact_beetrootSeeds");
        ShapedRecipe compactBeetrootSeedsRecipe = new ShapedRecipe(beetrootSeedsKey,beetrootSeedsItem);

        compactBeetrootSeedsRecipe.shape("BBB","BBB","BBB");

        compactBeetrootSeedsRecipe.setIngredient('B',Material.BEETROOT_SEEDS);
        return compactBeetrootSeedsRecipe;
    }

    public ShapelessRecipe uncompactBeetrootSeedsShapelessRecipe(){
        ItemStack beetrootSeedsItem = new ItemStack(Material.BEETROOT_SEEDS);
        ItemMeta beetrootSeedsMeta = beetrootSeedsItem.getItemMeta();
        beetrootSeedsMeta.setDisplayName(ChatColor.WHITE+"Pile of Beetroot Seeds");
        beetrootSeedsMeta.setCustomModelData(2);
        beetrootSeedsItem.setItemMeta(beetrootSeedsMeta);

        NamespacedKey beetrootSeedsKey = new NamespacedKey(plugin, "uncompact_beetroot_seeds");
        ShapelessRecipe uncompactBeetrootSeedsRecipe = new ShapelessRecipe(beetrootSeedsKey,new ItemStack(Material.BEETROOT_SEEDS,9));

        uncompactBeetrootSeedsRecipe.addIngredient(new RecipeChoice.ExactChoice(beetrootSeedsItem));
        return uncompactBeetrootSeedsRecipe;
    }

    public ShapedRecipe compactBeetrootShapedRecipe(){
        ItemStack beetrootItem = new ItemStack(Material.BEETROOT);
        ItemMeta beetrootMeta = beetrootItem.getItemMeta();
        beetrootMeta.setDisplayName(ChatColor.WHITE+"Pile of Beetroots");
        beetrootMeta.setCustomModelData(2);
        beetrootItem.setItemMeta(beetrootMeta);

        NamespacedKey beetrootKey = new NamespacedKey(plugin, "compact_beetroot");
        ShapedRecipe compactBeetrootRecipe = new ShapedRecipe(beetrootKey,beetrootItem);

        compactBeetrootRecipe.shape("BBB","BBB","BBB");

        compactBeetrootRecipe.setIngredient('B',Material.BEETROOT);
        return compactBeetrootRecipe;
    }

    public ShapelessRecipe uncompactBeetrootShapelessRecipe(){
        ItemStack beetrootItem = new ItemStack(Material.BEETROOT);
        ItemMeta beetrootMeta = beetrootItem.getItemMeta();
        beetrootMeta.setDisplayName(ChatColor.WHITE+"Pile of Beetroots");
        beetrootMeta.setCustomModelData(2);
        beetrootItem.setItemMeta(beetrootMeta);

        NamespacedKey beetrootKey = new NamespacedKey(plugin, "uncompact_beetroot");
        ShapelessRecipe uncompactBeetrootRecipe = new ShapelessRecipe(beetrootKey,new ItemStack(Material.BEETROOT,9));

        uncompactBeetrootRecipe.addIngredient(new RecipeChoice.ExactChoice(beetrootItem));
        return uncompactBeetrootRecipe;
    }

    public ShapedRecipe compactNetherWartShapedRecipe(){
        ItemStack netherWartItem = new ItemStack(Material.NETHER_WART);
        ItemMeta netherWartMeta = netherWartItem.getItemMeta();
        netherWartMeta.setDisplayName(ChatColor.WHITE+"Pile of Nether Warts");
        netherWartMeta.setCustomModelData(2);
        netherWartItem.setItemMeta(netherWartMeta);

        NamespacedKey netherWartKey = new NamespacedKey(plugin, "compact_nether_wart");
        ShapedRecipe compactNetherWartRecipe = new ShapedRecipe(netherWartKey,netherWartItem);

        compactNetherWartRecipe.shape("NNN","NNN","NNN");

        compactNetherWartRecipe.setIngredient('N',Material.NETHER_WART);
        return compactNetherWartRecipe;
    }

    public ShapelessRecipe uncompactNetherWartShapelessRecipe(){
        ItemStack netherWartItem = new ItemStack(Material.NETHER_WART);
        ItemMeta netherWartMeta = netherWartItem.getItemMeta();
        netherWartMeta.setDisplayName(ChatColor.WHITE+"Pile of Nether Warts");
        netherWartMeta.setCustomModelData(2);
        netherWartItem.setItemMeta(netherWartMeta);

        NamespacedKey netherWartKey = new NamespacedKey(plugin, "uncompact_nether_wart");
        ShapelessRecipe uncompactNetherWartRecipe = new ShapelessRecipe(netherWartKey,new ItemStack(Material.NETHER_WART,9));

        uncompactNetherWartRecipe.addIngredient(new RecipeChoice.ExactChoice(netherWartItem));
        return uncompactNetherWartRecipe;
    }
}
