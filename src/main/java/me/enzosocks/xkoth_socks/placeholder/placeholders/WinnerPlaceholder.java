package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.game.scoreTracker.IScoreTracker;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.schedulers.ScoreGameLoop;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class WinnerPlaceholder implements XPlaceholder {

	private static final String PLACEHOLDER = "winner";
	private final String defaultValue;

	public WinnerPlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return placeHolder.equals(PLACEHOLDER);
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		if (koth.getGame().getGameLoop() instanceof ScoreGameLoop) {
			return LocalPlaceholder.getInstance().onRequestKoth(player, koth, "scorePlayer1");
		}

		IScoreTracker scoreTracker = koth.getGame().getGameLoop().getScoreTracker();

		if (scoreTracker.getCapper() == null
				|| scoreTracker.getPoints(scoreTracker.getCapper()) < koth.getGame().getRules().getPointsToWin()) {
			return defaultValue;
		}
		return scoreTracker.getCapper().getName();
	}
}
