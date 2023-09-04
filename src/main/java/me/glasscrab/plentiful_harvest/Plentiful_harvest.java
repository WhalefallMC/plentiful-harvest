package me.glasscrab.plentiful_harvest;

import me.glasscrab.plentiful_harvest.Listeners.CropBreaks.*;
import me.glasscrab.plentiful_harvest.Listeners.FarmlandTrampleEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plentiful_harvest extends JavaPlugin {

    public static Plentiful_harvest plentiful_harvest;
    private Manager manager;

    @Override
    public void onEnable() {
        this.manager = new Manager();
        plentiful_harvest = this;
        
        this.getServer().getPluginManager().registerEvents(new WheatBreakEvent(this.manager), this);
        this.getServer().getPluginManager().registerEvents(new CarrotBreakEvent(this.manager), this);
        this.getServer().getPluginManager().registerEvents(new PotatoBreakEvent(this.manager), this);
        this.getServer().getPluginManager().registerEvents(new BeetrootBreakEvent(this.manager), this);
        this.getServer().getPluginManager().registerEvents(new NetherWartBreakEvent(this.manager), this);
        this.getServer().getPluginManager().registerEvents(new FarmlandTrampleEvent(), this);

        this.getLogger().info("Farming plugin has enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Farming plugin has disabled!");
    }
}
