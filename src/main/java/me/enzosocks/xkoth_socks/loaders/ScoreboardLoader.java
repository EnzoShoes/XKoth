package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.schedulers.scoreboards.KothScoreboard;
import org.bukkit.configuration.file.FileConfiguration;

public class ScoreboardLoader implements Loader<KothScoreboard> {
	@Override
	public KothScoreboard load(FileConfiguration config, String path, Object... args) {
		return null;
//		if (!config.contains(path + ".scoreboard")) {
//			return null;
//		}
//
//		List<String> lines = config.getStringList(path + ".scoreboard.lines");
//		if (lines.size() > 15) {
//			throw new IllegalArgumentException("Scoreboard lines cannot be more than 15.");
//		}
//
//		return new KothScoreboard(
//				config.getBoolean(path + ".scoreboard.enabled"),
//				config.getBoolean(path + ".scoreboard.only-show-when-capturing", false),
//				config.getInt(path + ".scoreboard.view-distance", 0),
//				config.getString(path + ".scoreboard.title", "&c%kothName% KOTH"),
//				config.getStringList(path + ".scoreboard.lines")
//		);
	}
}
