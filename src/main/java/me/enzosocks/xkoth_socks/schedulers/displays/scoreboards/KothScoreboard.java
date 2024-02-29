package me.enzosocks.xkoth_socks.schedulers.displays.scoreboards;

import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import me.enzosocks.xkoth_socks.utils.XScoreboard;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.entity.Player;

public class KothScoreboard extends XScoreboard {
	private ScoreboardConfig config;

	public KothScoreboard(ScoreboardConfig config) {
		super(config.getTitle(), config.getLines());
		this.config = config;
	}

	public void update(Player player, DisplayData displayData) {
		if (!shouldShowToPlayer(displayData.getCapper(), player, displayData.getCuboid())) {
			player.setScoreboard(null);
			return;
		}

		String placeholderedTitle = LocalPlaceholder
				.replacePlaceholders(player, config.getTitle(), displayData.getKothName());

		this.setTitle(MessageUtil.colorize(placeholderedTitle));
		for (int i = 0; i < config.getLines().size(); i++) {
			String placeholderedLine = LocalPlaceholder
					.replacePlaceholders(player, config.getLines().get(i), displayData.getKothName());

			this.setLine(i, MessageUtil.colorize(placeholderedLine));
		}

		player.setScoreboard(this.getScoreboard());
	}

	protected boolean shouldShowToPlayer(Player capturer, Player player, Cuboid cuboid) {
		if (config.isOnlyShowWhenCapturing())
			//TODO: When Player becomes refactored to handle teams, return actual team members here
			return player.getUniqueId() == capturer.getUniqueId();
		return isPlayerWithinViewDistance(player, cuboid);
	}

	protected boolean isPlayerWithinViewDistance(Player player, Cuboid cuboid) {
		if (config.getViewDistance() == -1)
			return player.getWorld().equals(cuboid.getWorld());
		if (config.getViewDistance() == 0)
			return true;
		return player.getLocation().distance(cuboid.getLowerNE()) <= config.getViewDistance();
	}
}
