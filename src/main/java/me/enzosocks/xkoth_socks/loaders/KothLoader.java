package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.enums.Mode;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.managers.GameLoopFactory;
import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class KothLoader implements Loader<Koth> {
	private XKoth plugin;

	public KothLoader(XKoth plugin) {
		this.plugin = plugin;
	}

	@Override
	public Koth load(FileConfiguration config, String path, Object... args) {
		String kothName = (String) args[0];
		ConfigurationSection currentKoth = config.getConfigurationSection(path);
		if (currentKoth == null) {
			throw new IllegalArgumentException("Koth " + kothName + " does not exist!");
		}

		World world = Bukkit.getWorld(currentKoth.getString("world", "world"));
		if (world == null) {
			throw new IllegalArgumentException("World " + currentKoth.getString("world") + " does not exist for koth " + kothName + " !");
		}

		Cuboid cuboid = new Cuboid(
				world,
				currentKoth.getConfigurationSection("corner1").getValues(false),
				currentKoth.getConfigurationSection("corner2").getValues(false)
		);

		Loader<KothSchedule> loader = new KothScheduleLoader();
		KothSchedule kothSchedule = loader.load(config, path + ".kothTimes");

		GameRules gameRules = new GameRules(
				currentKoth.getInt("rules.maxTime"),
				currentKoth.getInt("rules.pointsToWin"),
				currentKoth.getBoolean("rules.winnerIfTimeRunsOut")
		);

		List<String> commandsOnWin = currentKoth.getStringList("commandsOnWin");

		Mode mode = Mode.fromString(currentKoth.getString("mode"));

		Game game = new Game(plugin, kothName, gameRules, commandsOnWin);
		GameLoop gameLoop = GameLoopFactory.createGameLoop(game, cuboid, mode);
		game.setGameLoop(gameLoop);

		String displayName = currentKoth.getString("name");
		displayName = displayName == null ? kothName : displayName;

		return new Koth(kothName, displayName, game, kothSchedule);
	}
}
