package me.glasscrab.plentiful_harvest.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.glasscrab.plentiful_harvest.Manager;
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
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "A single one could feed 100 horses.");
                superCrop = Manager.getManager().makeSuperCrop(ChatColor.GOLD + "Hyper Carrot", Material.CARROT, lore, 1, amount);
            }

            case "beetroot" -> {
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "A beetroot so old it's been infused with magic.");
                superCrop = Manager.getManager().makeSuperCrop(ChatColor.LIGHT_PURPLE + "Mystic Beetroot", Material.BEETROOT, lore, 1, amount);
            }

            case "potato" -> {
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Opposite to the poisonous potato, and much rarer.");
                superCrop = Manager.getManager().makeSuperCrop(ChatColor.GREEN + "Medicinal Potato", Material.POTATO, lore, 1, amount);
            }

            case "netherwart" -> {
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Like a four leaf clover, found very rarely.");
                superCrop = Manager.getManager().makeSuperCrop(ChatColor.DARK_AQUA + "Warped Nether Wart", Material.NETHER_WART, lore, 1, amount);
            }

            case "wheat" -> {
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "A whole lot better than the other kind.");
                superCrop = Manager.getManager().makeSuperCrop(ChatColor.YELLOW + "Whole Wheat", Material.WHEAT, lore, 1, amount);
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