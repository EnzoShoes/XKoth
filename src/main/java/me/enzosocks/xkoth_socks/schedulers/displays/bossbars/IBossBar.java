package me.enzosocks.xkoth_socks.schedulers.displays.bossbars;

import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public abstract class IBossBar {
	protected BossBar bossbar;
	protected BossBarConfig config;

	public IBossBar(BossBarConfig config) {
		this.config = config;
		bossbar = Bukkit.createBossBar(config.getTitle(), config.getColor(), config.getStyle());
	}

	public abstract void update(Player player, DisplayData displayData);

	public void clear(Player player) {
		bossbar.removePlayer(player);
	}

	protected boolean isPlayerWithinViewDistance(Player player, Cuboid cuboid) {
		if (config.getViewDistance() == -1)
			return player.getWorld().equals(cuboid.getWorld());
		if (config.getViewDistance() == 0)
			return true;
		return player.getLocation().distance(cuboid.getLowerNE()) <= config.getViewDistance();
	}

	protected boolean shouldShowToPlayer(Player capturer, Player player, Cuboid cuboid) {
		if (config.isOnlyShowWhenCapturing())
			//TODO: When Player becomes refactored to handle teams, return actual team members here
			return player.getUniqueId() == player.getUniqueId();
		return isPlayerWithinViewDistance(player, cuboid);
	}
}
