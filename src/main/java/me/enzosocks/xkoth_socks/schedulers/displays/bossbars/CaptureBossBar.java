package me.enzosocks.xkoth_socks.schedulers.displays.bossbars;

import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.schedulers.CaptureGameLoop;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.entity.Player;

public class CaptureBossBar extends IBossBar {
	public CaptureBossBar(BossBarConfig config) {
		super(config);
	}

	@Override
	public void update(Player player, DisplayData displayData) {
		if (!shouldShowToPlayer(displayData.getCapper(), player, displayData.getCuboid())) {
			bossbar.removePlayer(player);
		}

		LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
		bossbar.setTitle(MessageUtil.colorize(placeholder.setPlaceholders(displayData.getKothName(), config.getTitle())));
		if (displayData instanceof CaptureGameLoop) {
			CaptureGameLoop captureGameLoop = (CaptureGameLoop) displayData;
			bossbar.setProgress(captureGameLoop.getPercentageCappingLeft());
		} else {
			bossbar.setProgress(displayData.getPercentageTimeLeft());
		}

		bossbar.addPlayer(player);
	}
}
