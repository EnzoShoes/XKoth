package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.schedulers.displays.scoreboards.ScoreboardManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ScoreboardManagerLoader implements Loader<ScoreboardManager> {

	@Override
	public ScoreboardManager load(FileConfiguration config, String path, Object... args) {
		if (!config.contains(path + ".scoreboard")) {
			return null;
		}

		List<String> lines = config.getStringList(path + ".scoreboard.lines");
		if (lines.size() > 15) {
			throw new IllegalArgumentException("Scoreboard lines cannot be more than 15.");
		}

		return new ScoreboardManager(
				config.getBoolean(path + ".scoreboard.enabled"),
				config.getBoolean(path + ".scoreboard.only-show-when-capturing", false),
				config.getInt(path + ".scoreboard.view-distance", 0),
				config.getString(path + ".scoreboard.title", "&c%kothName% KOTH"),
				config.getStringList(path + ".scoreboard.lines")
		);
	}
}
