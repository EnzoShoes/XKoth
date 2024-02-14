package me.enzosocks.xkoth_socks.schedulers.bossbars;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

/*
 * Bar goes down based on total time left.
 */
public class TimeBossBar extends IBossBar {
	public TimeBossBar() {
		super();
		setVisible(false);
	}

	@Override
	public void updateBossbar(Player capturer, int gameTimeInSeconds, Game game) {
		System.out.println("time bar");
		List<Player> playersToShowBar = getPlayersToShowBar(capturer, game.getCuboid());
		playersToShowBar.forEach(player -> {
			if (!bossbar.getPlayers().contains(player)) {
				bossbar.addPlayer(player);
			}
		});
		bossbar.getPlayers().forEach(player -> {
			if (!playersToShowBar.contains(player)) {
				bossbar.removePlayer(player);
			}
		});
		LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
		bossbar.setTitle(MessageUtil.colorize(placeholder.setPlaceholders(game.getKothName(), this.title)));
		bossbar.setProgress(1 - ((double) gameTimeInSeconds / game.getRules().getMaxTime()));
	}
}
