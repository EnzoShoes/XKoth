package me.enzosocks.xkoth_socks;

import me.enzosocks.xkoth_socks.commands.KothCommand;
import me.enzosocks.xkoth_socks.managers.ConfigManager;
import me.enzosocks.xkoth_socks.managers.KothManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class XKoth extends JavaPlugin {

    private ConfigManager configManager;
    private KothManager kothManager;

    private static JavaPlugin instance = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        configManager = new ConfigManager(this);
        kothManager = new KothManager(this);
        getCommand("koth").setExecutor(new KothCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public KothManager getKothManager() {
        return kothManager;
    }

    public static Plugin getInstance() {
        return instance;
    }
}
