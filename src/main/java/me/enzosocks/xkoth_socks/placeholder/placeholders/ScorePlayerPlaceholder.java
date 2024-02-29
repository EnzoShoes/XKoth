package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScorePlayerPlaceholder implements XPlaceholder {
	private static final Pattern pattern = Pattern.compile("scorePlayer(\\d+)|winner");
	private final String defaultValue;

	public ScorePlayerPlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return pattern.matcher(placeHolder).matches();
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		Matcher scorePlayerMatcher = pattern.matcher(placeholder);
		if (!scorePlayerMatcher.matches()) {
			throw new IllegalArgumentException("Invalid placeholder error");
		}

		int position = placeholder.equalsIgnoreCase("winner") ?
				1 : Integer.parseInt(scorePlayerMatcher.group(1));

		return koth.getGame().getScoreTracker().getPlayerForPosition(position)
				.map(OfflinePlayer::getName).orElse(defaultValue);
	}
}
