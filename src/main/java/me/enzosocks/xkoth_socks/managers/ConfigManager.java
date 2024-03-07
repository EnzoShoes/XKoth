package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

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

//	private Koth getKoth(String kothName) {
//		ConfigurationSection currentKoth = config.getConfigurationSection("koths." + kothName);
//		if (currentKoth == null) {
//			throw new IllegalArgumentException("Koth " + kothName + " does not exist!");
//		}
//
//		World world = Bukkit.getWorld(currentKoth.getString("world", "world"));
//		if (world == null) {
//			Logger.warning("World " + currentKoth.getString("world") + " does not exist!");
//			return null;
//		}
//
//		Cuboid cuboid = new Cuboid(
//				world,
//				currentKoth.getConfigurationSection("corner1").getValues(false),
//				currentKoth.getConfigurationSection("corner2").getValues(false)
//		);
//
//		Loader<KothSchedule> loader = new KothScheduleLoader();
//		KothSchedule kothSchedule = loader.load(config, "koths." + kothName + ".kothTimes");
//
//		GameRules gameRules = new GameRules(
//				currentKoth.getInt("rules.maxTime"),
//				currentKoth.getInt("rules.pointsToWin"),
//				currentKoth.getBoolean("rules.winnerIfTimeRunsOut")
//		);
//
//		List<String> commandsOnWin = currentKoth.getStringList("commandsOnWin");
//
//		String mode = currentKoth.getString("mode");
//
//		if (mode == null) {
//			Logger.warning("Koth " + kothName + " does not have a mode set, defaulting to score mode.");
//			mode = "score";
//		}
//
//		Game game = new Game(kothName, gameRules, commandsOnWin);
//
//		//TODO: replace with factory
//		GameLoop gameLoop = null;
//		if (mode.equalsIgnoreCase("score")) {
//			gameLoop = new ScoreGameLoop(game, cuboid, new ScoreTracker());
//		} else if (mode.equalsIgnoreCase("capture")) {
//			gameLoop = new CaptureGameLoop(game, cuboid, new CaptureTracker());
//		}
//
//		game.setGameLoop(gameLoop);
//
//		String displayName = currentKoth.getString("name");
//		displayName = displayName == null ? kothName : displayName;
//
//		return new Koth(kothName, displayName, game, kothSchedule);
//	}
//
//	public List<Koth> getKoths() {
//		List<Koth> koths = new ArrayList<>();
//		for (String kothName : this.config.getConfigurationSection("koths").getKeys(false)) {
//			Koth koth = getKoth(kothName);
//			if (koth != null)
//				koths.add(koth);
//		}
//		return koths;
//	}

	public FileConfiguration getConfig() {
		return config;
	}
}
