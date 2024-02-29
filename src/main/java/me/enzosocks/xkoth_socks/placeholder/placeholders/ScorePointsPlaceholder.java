package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScorePointsPlaceholder implements XPlaceholder {
	private static final Pattern pattern = Pattern.compile("scorePoints(\\d+)");
	private final String defaultValue;

	public ScorePointsPlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return pattern.matcher(placeHolder).matches();
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		Matcher scorePointsMatcher = pattern.matcher(placeholder);
		if (!scorePointsMatcher.matches()) {
			throw new IllegalArgumentException("Invalid placeholder error");
		}

		int position = Integer.parseInt(scorePointsMatcher.group(1));
		return koth.getGame().getScoreTracker().getPointsForPosition(position)
				.map(Object::toString).orElse(defaultValue);
	}
}
