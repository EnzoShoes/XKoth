package me.enzosocks.xkoth_socks.schedulers.bossbars;

import me.enzosocks.xkoth_socks.instance.game.Game;
import org.bukkit.entity.Player;

public class NoBossBar extends IBossBar {
	public NoBossBar() {
	}

	@Override
	public void updateBossbar(Player capturer, int gameTimeInSeconds, Game game) {
		// nothing
		System.out.println("nothing");
	}
}
