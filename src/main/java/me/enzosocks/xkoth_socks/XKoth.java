package me.enzosocks.xkoth_socks;

import me.enzosocks.xkoth_socks.commands.CommandHandler;
import me.enzosocks.xkoth_socks.commands.KothCommand;
import me.enzosocks.xkoth_socks.managers.ConfigManager;
import me.enzosocks.xkoth_socks.managers.KothManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class XKoth extends JavaPlugin {

    private ConfigManager configManager;
    private KothManager kothManager;

    private List<Loader> loaders;

    private static JavaPlugin instance = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        configManager = new ConfigManager(this);
        kothManager = new KothManager(this);

        loaders = new ArrayList<>();
        loaders.add(new CommandHandler(this));
        loaders.forEach(Loader::load);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        loaders.forEach(Loader::unload);
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
