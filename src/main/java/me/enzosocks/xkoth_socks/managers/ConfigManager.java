package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

	private XKoth plugin;
	private FileConfiguration config;

	public ConfigManager(XKoth plugin) {
		this.config = plugin.getConfig();
		this.plugin = plugin;
		plugin.saveDefaultConfig();
	}

	public void saveKoths(List<Koth> koths) {
		for (Koth koth : koths) {
			config.set("koths." + koth.getName() + ".world", koth.getCuboid().getWorld().getName());
			config.set("koths." + koth.getName() + ".corner1", koth.getCuboid().getLowerNE().toVector());
			config.set("koths." + koth.getName() + ".corner2", koth.getCuboid().getUpperSW().toVector());
			config.set("koths." + koth.getName() + ".kothTimes", koth.getStartTimes());
			config.set("koths." + koth.getName() + ".commandsOnWin", koth.getCommandsOnWin());
			ConfigurationSection settings = config.createSection("koths." + koth.getName() + ".settings");
			settings.set("pointsToWin", koth.getPointsToWin());
			settings.set("maxTime", koth.getMaxTime());
			settings.set("winerIfTimeRunsOut", koth.isWinnerIfTimeRunsOut());
		}
		plugin.saveConfig();
	}

	private Koth getKoth(String kothName) {
		World world = Bukkit.getWorld(config.getString("koths." + kothName + ".world"));
		if (world == null) {
			plugin.getLogger().warning("World " + config.getString("koths." + kothName + ".world") + " does not exist!");
			return null;
		}

		Cuboid cuboid = new Cuboid(
				world,
				config.getConfigurationSection("koths." + kothName + ".corner1").getValues(false),
				config.getConfigurationSection("koths." + kothName + ".corner2").getValues(false)
		);

		List<LocalTime> startTimes = new ArrayList<>();
		config.getStringList("koths." + kothName + ".kothTimes").forEach(time -> {
			String[] split = time.split(":");
			int hour = Integer.parseInt(split[0]);
			int minute = Integer.parseInt(split[1]);
			startTimes.add(LocalTime.of(hour, minute, 0));
		});

		return new Koth(
				kothName,
				cuboid,
				startTimes,
				config.getInt("koths." + kothName + ".pointsToWin"),
				config.getStringList("koths." + kothName + ".commandsOnWin")
		);
	}

	private KothSettings getKothSettings(String kothName) {
		ConfigurationSection settingsSection = this.config.getConfigurationSection("koths." + kothName + ".settings");
		if (settingsSection == null)
			return null;
		return new KothSettings(
				settingsSection.getLong("settings.maxTime"),
				settingsSection.getInt("settings.pointsToWin"),
				settingsSection.getBoolean("settings.winnerIfTimeRunsOut")
		);
	}

	public List<Koth> getKoths() {
		List<Koth> koths = new ArrayList<>();
		for (String kothName : this.config.getConfigurationSection("koths").getKeys(false)) {
			Koth koth = getKoth(kothName);
			if (koth != null)
				koths.add(getKoth(kothName));
		}
		return koths;
	}

}
