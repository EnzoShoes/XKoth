package me.enzosocks.xkoth_socks.managers.leaderboards;

import me.enzosocks.xkoth_socks.Savable;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.utils.Persist;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LeaderboardManager implements Savable {
	private transient XKoth plugin;

	// Separated to improve lookup times for placeholders
	public static Map<String, List<Date>> pastPlayerWins;
	public static Map<String, List<Date>> pastTeamWins;

	public LeaderboardManager(XKoth plugin) {
		this.plugin = plugin;
	}

	@Override
	public void save(Persist persist) {
		persist.save(this, "leaderboard");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, LeaderboardManager.class, "leaderboard");
	}
}
