package me.enzosocks.xkoth_socks.schedulers.bossbars;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class IBossBar {
	protected BossBar bossbar;
	private boolean onlyShowWhenCapturing;
	private int viewDistance = 0;
	private String title;

	public IBossBar() {
		bossbar = Bukkit.createBossBar("test", BarColor.RED, BarStyle.SOLID);
	}

	public abstract void updateBossbar(Player capturer, int timePassed, Game game);

	public void setVisible(boolean visible) {
		bossbar.setVisible(visible);
	}

	protected List<Player> getPlayersToShowBar(Player capturer, Cuboid cuboid) {
		if (onlyShowWhenCapturing)
			//TODO: When Player becomes refactored to handle teams, return actual team members here
			return capturer == null ? new ArrayList<>() : Arrays.asList(capturer);
		return getPlayersWithinViewDistance(cuboid);
	}

	protected List<Player> getPlayersWithinViewDistance(Cuboid cuboid) {
		if (viewDistance == -1)
			return Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(cuboid.getWorld())).collect(Collectors.toList());
		if (viewDistance == 0)
			return new ArrayList<>(Bukkit.getOnlinePlayers());
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> player.getLocation().distance(cuboid.getLowerNE()) <= this.viewDistance)
				.collect(Collectors.toList());
	}

	protected String getFormattedBossbarTitle(Player capturer, String kothName, int gameTime, long maxTime) {
		String formattedTitle = this.title;
		formattedTitle = formattedTitle.replace("%kothName%", kothName);
		formattedTitle = formattedTitle.replace("%player%", capturer == null ? "---" : capturer.getName());

		long remainingTimeInSeconds = maxTime - gameTime;
		String timeLeftFormatted = String.format("%02d:%02d", remainingTimeInSeconds / 60, remainingTimeInSeconds % 60);
		formattedTitle = formattedTitle.replace("%timeLeft%", timeLeftFormatted);
		return ChatColor.translateAlternateColorCodes('&', formattedTitle);
	}

	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}

	public void setName(String name) {
		this.title = name;
	}

	public void setStyle(BarStyle style) {
		bossbar.setStyle(style);
	}

	public void setColor(BarColor color) {
		bossbar.setColor(color);
	}

	public void setOnlyShowWhenCapturing(boolean onlyShowWhenCapturing) {
		this.onlyShowWhenCapturing = onlyShowWhenCapturing;
	}
}
