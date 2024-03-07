package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.enums.Mode;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.scoreTracker.CaptureTracker;
import me.enzosocks.xkoth_socks.instance.game.scoreTracker.ScoreTracker;
import me.enzosocks.xkoth_socks.schedulers.CaptureGameLoop;
import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.schedulers.ScoreGameLoop;
import me.enzosocks.xkoth_socks.utils.Cuboid;

public class GameLoopFactory {
	public static GameLoop createGameLoop(Game game, Cuboid cuboid, Mode mode) {
		switch (mode) {
			case SCORE:
				return new ScoreGameLoop(game, cuboid, new ScoreTracker());
			case CAPTURE:
				return new CaptureGameLoop(game, cuboid, new CaptureTracker());
			default:
				throw new IllegalArgumentException("Invalid mode: " + mode);
		}
	}

	public static GameLoop createGameLoop(Game game, Cuboid cuboid) {
		return createGameLoop(game, cuboid, Mode.SCORE);
	}
}
