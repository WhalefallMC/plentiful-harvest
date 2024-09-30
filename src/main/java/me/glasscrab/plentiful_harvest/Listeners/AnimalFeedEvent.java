package me.glasscrab.plentiful_harvest.Listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class AnimalFeedEvent implements Listener {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    @EventHandler
    public void feedAnimal(PlayerInteractEntityEvent e) {

        if(e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();


        if(isSuperCrop(hand)) {
            e.setCancelled(true);

            if(hand.getType().equals(Material.POTATO) && e.getRightClicked() instanceof LivingEntity && !e.getPlayer().isSneaking()){
                ((LivingEntity) e.getRightClicked()).setHealth(((LivingEntity) e.getRightClicked()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                e.getPlayer().getWorld().spawnParticle(Particle.HAPPY_VILLAGER, ((LivingEntity) e.getRightClicked()).getEyeLocation(),15,.3,.3,.3);
                e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.MASTER, 1,1);
                e.getPlayer().sendActionBar(miniMessage.deserialize("<green>" + e.getRightClicked().getType() + " has been healed!</green>"));
                hand.setAmount(hand.getAmount()-1);
            }

            if(hand.getType().equals(Material.CARROT) && e.getRightClicked() instanceof AbstractHorse){
                if(((AbstractHorse) e.getRightClicked()).getJumpStrength() == 1 || ((AbstractHorse) e.getRightClicked()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() == 0.3375){
                    e.getPlayer().sendActionBar(miniMessage.deserialize("<red>This " + e.getRightClicked().getType() + " has already been fed a Hyper Carrot!</red>"));
                    return;
                }
                ((AbstractHorse) e.getRightClicked()).setJumpStrength(1);
                ((AbstractHorse) e.getRightClicked()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3375);
                ((AbstractHorse) e.getRightClicked()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                e.getPlayer().getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, ((LivingEntity) e.getRightClicked()).getEyeLocation(),15,.8,.8,.8,.2);
                e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_HORSE_EAT, SoundCategory.MASTER, 1,1);
                e.getPlayer().sendActionBar(miniMessage.deserialize("<gold>This " + e.getRightClicked().getType() + " feels stronger..</gold>"));
                hand.setAmount(hand.getAmount()-1);
            }

        }
    }

    private boolean isSuperCrop(ItemStack item) {
        return (item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 1);
    }
}
