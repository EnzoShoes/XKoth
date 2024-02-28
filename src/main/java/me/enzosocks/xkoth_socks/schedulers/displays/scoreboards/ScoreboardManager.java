package me.enzosocks.xkoth_socks.schedulers.displays.scoreboards;

import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager extends DisplayManager implements ScoreboardConfig {
	private final Map<UUID, KothScoreboard> playerScoreboards = new HashMap<>();
	private final String title;
	private final List<String> lines;

	public ScoreboardManager(boolean enabled, boolean onlyShowWhenCapturing, int viewDistance, String title, List<String> lines) {
		super(enabled, viewDistance, onlyShowWhenCapturing);
		this.title = title;
		this.lines = lines;
	}

	protected void doUpdate(DisplayData displayData) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!playerScoreboards.containsKey(player.getUniqueId())) {
				playerScoreboards.put(player.getUniqueId(), new KothScoreboard(this));
			}
			KothScoreboard scoreboard = playerScoreboards.get(player.getUniqueId());
			scoreboard.update(player, displayData);
		}
	}

	public void clear() {
		for (UUID uuid : playerScoreboards.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null && player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
				player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		}
		playerScoreboards.clear();
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
