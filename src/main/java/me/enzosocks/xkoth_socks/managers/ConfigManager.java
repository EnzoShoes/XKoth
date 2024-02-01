package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {

	private XKoth plugin;
	private FileConfiguration config;

	public ConfigManager(XKoth plugin) {
		plugin.saveDefaultConfig();
		this.config = plugin.getConfig();
		this.plugin = plugin;
	}

	public void saveKoths(List<Koth> koths) {
		for (Koth koth : koths) {
			config.set("koths." + koth.getName() + ".world", koth.getCuboid().getWorld().getName());
			config.set("koths." + koth.getName() + ".corner1.x", koth.getCuboid().getLowerNE().getX());
			config.set("koths." + koth.getName() + ".corner1.y", koth.getCuboid().getLowerNE().getY());
			config.set("koths." + koth.getName() + ".corner1.z", koth.getCuboid().getLowerNE().getZ());
			config.set("koths." + koth.getName() + ".corner2.x", koth.getCuboid().getUpperSW().getX());
			config.set("koths." + koth.getName() + ".corner2.y", koth.getCuboid().getUpperSW().getY());
			config.set("koths." + koth.getName() + ".corner2.z", koth.getCuboid().getUpperSW().getZ());
			List<String> startTimes = koth.getStartTimes().stream()
					.map(time -> String.format("%02d:%02d", time.getHour(), time.getMinute()))
					.collect(Collectors.toList());
			config.set("koths." + koth.getName() + ".kothTimes", startTimes);
			config.set("koths." + koth.getName() + ".commandsOnWin", koth.getCommandsOnWin());

			ConfigurationSection rules = config.createSection("koths." + koth.getName() + ".rules");
			rules.set("pointsToWin", koth.getRules().getPointsToWin());
			rules.set("maxTime", koth.getRules().getPointsToWin());
			rules.set("winerIfTimeRunsOut", koth.getRules().getPointsToWin());
		}
		plugin.saveConfig();
	}

	private Koth getKoth(String kothName) {
		ConfigurationSection currentKoth = config.getConfigurationSection("koths." + kothName);
		if (currentKoth == null) {
			throw new IllegalArgumentException("Koth " + kothName + " does not exist!");
		}

		World world = Bukkit.getWorld(currentKoth.getString("world", "world"));
		if (world == null) {
			plugin.getLogger().warning("World " + currentKoth.getString("world") + " does not exist!");
			return null;
		}

		Cuboid cuboid = new Cuboid(
				world,
				currentKoth.getConfigurationSection("corner1").getValues(false),
				currentKoth.getConfigurationSection("corner2").getValues(false)
		);

		//TODO: Replace with KothSchedule loader
		List<LocalTime> startTimes = new ArrayList<>();
		currentKoth.getStringList("kothTimes").forEach(time -> {
			String[] split = time.split(":");
			int hour = Integer.parseInt(split[0]);
			int minute = Integer.parseInt(split[1]);
			startTimes.add(LocalTime.of(hour, minute, 0));
		});
		KothSchedule kothSchedule = new KothSchedule(startTimes);

		GameRules gameRules = new GameRules(
				currentKoth.getInt("rules.maxTime"),
				currentKoth.getInt("rules.pointsToWin"),
				currentKoth.getBoolean("rules.winnerIfTimeRunsOut")
		);

		List<String> commandsOnWin = currentKoth.getStringList("commandsOnWin");

		Game game = new Game(kothName, cuboid, gameRules, commandsOnWin);

		return new Koth(kothName, game, kothSchedule);
	}

	public List<Koth> getKoths() {
		List<Koth> koths = new ArrayList<>();
		for (String kothName : this.config.getConfigurationSection("koths").getKeys(false)) {
			Koth koth = getKoth(kothName);
			if (koth != null)
				koths.add(koth);
		}
		return koths;
	}

	public FileConfiguration getConfig() {
		return config;
	}
}
