package me.glasscrab.plentiful_harvest;

import me.glasscrab.plentiful_harvest.Listeners.CropBreaks.*;
import me.glasscrab.plentiful_harvest.Listeners.AnimalFeedEvent;
import me.glasscrab.plentiful_harvest.Listeners.FarmlandTrampleEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plentiful_harvest extends JavaPlugin {

    public static Plentiful_harvest plentiful_harvest;
    @Override
    public void onEnable() {
        Manager manager = new Manager();
        plentiful_harvest = this;
        
        this.getServer().getPluginManager().registerEvents(new WheatBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new CarrotBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new PotatoBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new BeetrootBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new NetherWartBreakEvent(manager), this);
        this.getServer().getPluginManager().registerEvents(new FarmlandTrampleEvent(), this);
        this.getServer().getPluginManager().registerEvents(new AnimalFeedEvent(), this);

        this.getLogger().info("Farming plugin has enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Farming plugin has disabled!");
    }
}
