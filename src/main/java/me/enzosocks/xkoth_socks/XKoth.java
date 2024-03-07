package me.enzosocks.xkoth_socks;

import me.enzosocks.xkoth_socks.commands.CommandHandler;
import me.enzosocks.xkoth_socks.managers.ConfigManager;
import me.enzosocks.xkoth_socks.managers.KothManager;
import me.enzosocks.xkoth_socks.managers.leaderboards.LeaderboardManager;
import me.enzosocks.xkoth_socks.placeholder.DistantPlaceholder;
import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class XKoth extends XPlugin {

	private ConfigManager configManager = new ConfigManager(this);
	private KothManager kothManager = new KothManager(this);
	private LeaderboardManager leaderboardManager = new LeaderboardManager(this);
	private LocalPlaceholder localPlaceholder;

	private List<Loader> loaders = new ArrayList<>();
	private List<Savable> savables = new ArrayList<>();

	private static JavaPlugin instance = null;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;

		this.preEnable();

		new Logger("&cXKoth");
		Logger.info("Starting XKoth...");

		loaders.add(new CommandHandler(this));
		loaders.forEach(Loader::load);

		savables.add(leaderboardManager);
		savables.add(kothManager);
		savables.forEach(savable -> savable.load(this.getPersist()));

		localPlaceholder = new LocalPlaceholder(this);

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new DistantPlaceholder(localPlaceholder).register();
		}
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

	public LeaderboardManager getLeaderboardManager() {
		return leaderboardManager;
	}
}
