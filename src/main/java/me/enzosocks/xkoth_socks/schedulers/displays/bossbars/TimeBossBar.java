package me.enzosocks.xkoth_socks.schedulers.displays.bossbars;

import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.entity.Player;

/*
 * Bar goes down based on total time left.
 */
public class TimeBossBar extends IBossBar {
	public TimeBossBar(BossBarConfig config) {
		super(config);
	}

	@Override
	public void update(Player player, DisplayData displayData) {
		if (!shouldShowToPlayer(displayData.getCapper(), player, displayData.getCuboid())) {
			bossbar.removePlayer(player);
		}

		LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
		bossbar.setTitle(MessageUtil.colorize(placeholder.setPlaceholders(displayData.getKothName(), config.getTitle())));
		bossbar.setProgress(displayData.getPercentageTimeLeft());

		bossbar.addPlayer(player);
	}
}
