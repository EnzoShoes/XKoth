package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.StopReason;
import me.enzosocks.xkoth_socks.instance.game.scoreTracker.IScoreTracker;
import me.enzosocks.xkoth_socks.loaders.BossBarManagerLoader;
import me.enzosocks.xkoth_socks.loaders.Loader;
import me.enzosocks.xkoth_socks.loaders.ScoreboardManagerLoader;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayManager;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureGameLoop extends AbstractGameLoop implements DisplayData {
	private BukkitRunnable runnable;
	private final List<DisplayManager> displayManagers;

	public CaptureGameLoop(Game game, Cuboid cuboid, IScoreTracker scoreTracker) {
		super(game, cuboid, scoreTracker);
		//TODO: Everything should load with loaders eventually, config and path will be passed between loaders

		List<Loader<? extends DisplayManager>> displayManagerLoaders = Arrays.asList(
				new BossBarManagerLoader(),
				new ScoreboardManagerLoader()
		);

		displayManagers = displayManagerLoaders.stream()
				.map(loader -> loader.load(XKoth.getInstance().getConfig(), "koths." + game.getKothName()))
				.collect(Collectors.toList());
	}

	@Override
	public void startLoop() {
		runnable = new BukkitRunnable() {
			@Override
			public void run() {
				countPoints();
				for (DisplayManager displayManager : displayManagers) {
					displayManager.update(CaptureGameLoop.this);
				}
				checkForCapture();
				checkForTimeout();
			}
		};

		runnable.runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	private void checkForCapture() {
		if (scoreTracker.getPoints(scoreTracker.getCapper()) >= game.getRules().getPointsToWin()) {
			game.stop(StopReason.CAPTURE);
		}
	}

	private void checkForTimeout() {
		if (game.getRules().getMaxTime() != -1 && ++gameTime > game.getRules().getMaxTime()) {
			game.stop(StopReason.TIMEOUT_NO_WINNER);
		}
	}

	@Override
	public void stopLoop() {
		runnable.cancel();
		gameTime = 0;
		scoreTracker.clear();
		for (DisplayManager displayManager : displayManagers) {
			displayManager.clear();
		}
	}

	public double getPercentageCappingLeft() {
		return (double) (game.getRules().getPointsToWin() - scoreTracker.getPoints(scoreTracker.getCapper())) / game.getRules().getPointsToWin();
	}
}
