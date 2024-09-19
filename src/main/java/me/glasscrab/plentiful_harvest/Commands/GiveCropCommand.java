package me.glasscrab.plentiful_harvest.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.glasscrab.plentiful_harvest.Manager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class GiveCropCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        var miniMessage = MiniMessage.miniMessage();

        if(!(sender instanceof Player player)) {
            sender.sendMessage(miniMessage.deserialize("<red>You must be a player to use this command.</red>"));
            return true;
        }

        if(!player.hasPermission("farming.givecrop")) {
            sender.sendMessage(miniMessage.deserialize("<red>You do not have permission to use this command.</red>"));
            return true;
        }

        if(args.length != 3) {
            sender.sendMessage(miniMessage.deserialize("<red>Usage: /givecrop <crop> <player> <amount></red>"));
            return true;
        }

        String crop = args[0];
        Player target = player.getServer().getPlayer(args[1]);
        int amount = Integer.parseInt(args[2]);

        if(target == null) {
            sender.sendMessage(miniMessage.deserialize("<red>Player not found.</red>"));
            return true;
        }

        if(amount < 1) {
            sender.sendMessage(miniMessage.deserialize("<red>Amount must be greater than 0.</red>"));
            return true;
        }

        ItemStack superCrop = null;

        switch (crop) {
            case "carrot" -> {
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize("<gray>A single one could feed 100 horses."));
                superCrop = Manager.getManager().makeSuperCrop(miniMessage.deserialize("<gold>Hyper Carrot</gold>"), Material.CARROT, lore, 1, amount);
            }

            case "beetroot" -> {
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize("<gray>A beetroot so old it's been infused with magic.</gray>"));
                superCrop = Manager.getManager().makeSuperCrop(miniMessage.deserialize("<light_purple>Mystic Beetroot</light_purple>"), Material.BEETROOT, lore, 1, amount);
            }

            case "potato" -> {
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize("<gray>Opposite to the poisonous potato, and much rarer.</gray>"));
                superCrop = Manager.getManager().makeSuperCrop(miniMessage.deserialize("<green>Medicinal Potato</green>"), Material.POTATO, lore, 1, amount);
            }

            case "netherwart" -> {
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize("<gray>Like a four leaf clover, found very rarely.</gray>"));
                superCrop = Manager.getManager().makeSuperCrop(miniMessage.deserialize("<dark_aqua>Warped Nether Wart</dark_aqua>"), Material.NETHER_WART, lore, 1, amount);
            }

            case "wheat" -> {
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize("<gray>A whole lot better than the other kind.</gray>"));
                superCrop = Manager.getManager().makeSuperCrop(miniMessage.deserialize("<yellow>Whole Wheat</yellow>"), Material.WHEAT, lore, 1, amount);
            }
        }

        if(superCrop == null) {
            sender.sendMessage(miniMessage.deserialize("<red>Invalid crop.</red>"));
            return true;
        }

        Manager.getManager().giveSuperCrop(target, superCrop);
        sender.sendMessage(miniMessage.deserialize("<green>You gave " + target.getName() + " " + amount + " " + crop + " super crops.</green>"));

        return true;
    }
}