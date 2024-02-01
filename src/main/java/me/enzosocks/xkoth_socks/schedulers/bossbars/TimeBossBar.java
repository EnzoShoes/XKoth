package me.enzosocks.xkoth_socks.schedulers.bossbars;

import me.enzosocks.xkoth_socks.instance.game.Game;
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
		bossbar.setTitle(getFormattedBossbarTitle(capturer, game.getKothName(), gameTimeInSeconds, game.getRules().getMaxTime()));
		bossbar.setProgress(1 - ((double) gameTimeInSeconds / game.getRules().getMaxTime()));
	}
}
