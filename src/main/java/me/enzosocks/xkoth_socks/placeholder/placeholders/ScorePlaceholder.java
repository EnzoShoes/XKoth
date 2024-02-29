package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class ScorePlaceholder implements XPlaceholder {
	private static final String placeholder = "score";
	private final String defaultValue;

	public ScorePlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return placeHolder.equalsIgnoreCase(placeholder);
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		try {
			return String.valueOf(koth.getGame().getScoreTracker().getPoints(player.getUniqueId()));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
