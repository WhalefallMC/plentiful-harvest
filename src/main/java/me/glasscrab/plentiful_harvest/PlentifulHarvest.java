package me.glasscrab.plentiful_harvest;

import me.glasscrab.plentiful_harvest.Commands.GiveCropCommand;
import me.glasscrab.plentiful_harvest.Listeners.CropBreaks.*;
import me.glasscrab.plentiful_harvest.Listeners.AnimalFeedEvent;
import me.glasscrab.plentiful_harvest.Listeners.FarmlandTrampleEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlentifulHarvest extends JavaPlugin {

    public static PlentifulHarvest INSTANCE;
    @Override
    public void onEnable() {
        Manager manager = new Manager();
        INSTANCE = this;
        
        this.getServer().getPluginManager().registerEvents(new CropBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new FarmlandTrampleEvent(), this);
        this.getServer().getPluginManager().registerEvents(new AnimalFeedEvent(), this);

        this.getCommand("givecrop").setExecutor(new GiveCropCommand());

        this.getLogger().info("Farming plugin has enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Farming plugin has disabled!");
    }
}
