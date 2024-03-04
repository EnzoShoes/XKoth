package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.game.scoreTracker.ScoreTracker;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.Logger;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScorePlayerPlaceholder implements XPlaceholder {
	private static final Pattern pattern = Pattern.compile("scorePlayer(\\d+)");
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


		if (!(koth.getGame().getScoreTracker() instanceof ScoreTracker)) {
			Logger.warning(String.format("Placeholder %s is not valid for koth %s. This placeholder is only usable for score mode", placeholder, koth.getDisplayName()));
			return "Invalid placeholder. Check console.";
		}

		ScoreTracker scoreTracker = (ScoreTracker) koth.getGame().getScoreTracker();

		int position = Integer.parseInt(scorePlayerMatcher.group(1));

		return scoreTracker.getPlayerForPosition(position)
				.map(OfflinePlayer::getName).orElse(defaultValue);
	}
}
