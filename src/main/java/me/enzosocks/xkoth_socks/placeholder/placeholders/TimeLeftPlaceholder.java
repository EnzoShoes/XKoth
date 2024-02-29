package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.TimeUtil;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class TimeLeftPlaceholder implements XPlaceholder {

	private static final String placeholder = "timeLeft";
	private final String defaultValue;

	public TimeLeftPlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	@Override
	public boolean matches(String placeHolder) {
		return placeholder.equalsIgnoreCase(placeHolder);
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		Game game = koth.getGame();
		if (game != null) {
			return TimeUtil.formatTime(koth.getGame().getGameLoop().getTimeLeft());
		}
		return defaultValue;
	}
}
