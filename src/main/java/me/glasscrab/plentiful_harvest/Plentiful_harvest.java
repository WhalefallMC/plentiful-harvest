package me.glasscrab.plentiful_harvest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plentiful_harvest extends JavaPlugin {

    public static Plentiful_harvest plentiful_harvest;

    @Override
    public void onEnable() {
        new Manager();
        plentiful_harvest = this;
        Bukkit.getPluginManager().registerEvents(new WheatBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CarrotBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PotatoBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BeetrootBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NetherWartBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new FarmlandTrampleEvent(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
