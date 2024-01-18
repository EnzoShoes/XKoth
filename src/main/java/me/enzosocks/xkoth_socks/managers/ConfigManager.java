package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.utils.Cuboid;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Koth;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

	private XKoth plugin;
	private FileConfiguration config;

	public ConfigManager(XKoth plugin) {
		this.config = plugin.getConfig();
		plugin.saveDefaultConfig();
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
