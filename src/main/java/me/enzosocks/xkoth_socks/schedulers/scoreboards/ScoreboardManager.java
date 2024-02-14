package me.enzosocks.xkoth_socks.schedulers.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager implements ScoreboardConfig {
	private final Map<UUID, KothScoreboard> playerScoreboards = new HashMap<>();
	private final boolean enabled;
	private final boolean onlyShowWhenCapturing;
	private final int viewDistance;
	private final String title;
	private final List<String> lines;

	public ScoreboardManager(boolean enabled, boolean onlyShowWhenCapturing, int viewDistance, String title, List<String> lines) {
		this.enabled = enabled;
		this.onlyShowWhenCapturing = onlyShowWhenCapturing;
		this.viewDistance = viewDistance;
		this.title = title;
		this.lines = lines;
	}

	public void updateScoreboards(ScoreboardData scoreboardData) {
		if (!enabled)
			return;

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!playerScoreboards.containsKey(player.getUniqueId())) {
				playerScoreboards.put(player.getUniqueId(), new KothScoreboard(this));
			}
			KothScoreboard scoreboard = playerScoreboards.get(player.getUniqueId());
			scoreboard.update(player, scoreboardData);
		}
	}

	public void clearScoreboards() {
		for (UUID uuid : playerScoreboards.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null && player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
				player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
			}
		}
		playerScoreboards.clear();
	}

	@Override
	public boolean isOnlyShowWhenCapturing() {
		return onlyShowWhenCapturing;
	}

	@Override
	public int getViewDistance() {
		return viewDistance;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<String> getLines() {
		return lines;
	}
}
