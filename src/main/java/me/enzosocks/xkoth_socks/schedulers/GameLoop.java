package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.StopReason;
import me.enzosocks.xkoth_socks.loaders.BossBarManagerLoader;
import me.enzosocks.xkoth_socks.loaders.Loader;
import me.enzosocks.xkoth_socks.loaders.ScoreboardManagerLoader;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayManager;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameLoop implements DisplayData {
	private Game game;
	private Cuboid cuboid;
	private BukkitRunnable runnable;
	private Player capturer;
	private int gameTime = 0; // time left in seconds before end of game
	private final List<DisplayManager> displayManagers;

	public GameLoop(Game game, Cuboid cuboid) {
		this.game = game;
		this.cuboid = cuboid;
		//TODO: Everything should load with loaders eventually, config and path will be passed between loaders
		//=> XKoth.getInstance() nonsense will be gone
		List<Loader<? extends DisplayManager>> displayManagerLoaders = Arrays.asList(
				new BossBarManagerLoader(),
				new ScoreboardManagerLoader()
		);

		displayManagers = displayManagerLoaders.stream()
				.map(loader -> loader.load(XKoth.getInstance().getConfig(), "koths." + game.getKothName()))
				.collect(Collectors.toList());
	}

	public void startLoop() {
		runnable = new BukkitRunnable() {
			@Override
			public void run() {
				countPoints();
				for (DisplayManager displayManager : displayManagers) {
					displayManager.update(GameLoop.this);
				}
				checkForTimeout();
			}
		};

		runnable.runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	private void checkForTimeout() {
		if (game.getRules().getMaxTime() != -1 && ++gameTime >= game.getRules().getMaxTime()) {
			if (game.getScoreAtPosition(1).isPresent()) {
				game.stop(StopReason.TIMEOUT);
			} else {
				game.stop(StopReason.TIMEOUT_NO_WINNER);
			}
		}
	}

	private void countPoints() {
		if (capturer == null || !cuboid.contains(capturer.getLocation())) {
			capturer = getPlayerInCuboid();
		}
		if (capturer != null) {
			game.addPoint(capturer, 10);
		}
	}


	public void stopLoop() {
		runnable.cancel();
		gameTime = 0;
		for (DisplayManager displayManager : displayManagers) {
			displayManager.clear();
		}
	}

	private Player getPlayerInCuboid() {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> cuboid.contains(player.getLocation()))
				.findFirst().orElse(null);
	}

	private List<Player> getPlayersInCuboid() {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> cuboid.contains(player.getLocation()))
				.collect(Collectors.toList());
	}

	@Override
	public Cuboid getCuboid() {
		return cuboid;
	}

	public long getTimeLeft() {
		return game.getRules().getMaxTime() - gameTime;
	}

	public double getPercentageTimeLeft() {
		return 1D - (double) gameTime / game.getRules().getMaxTime();
	}

	@Override
	public Player getCapper() {
		return capturer;
	}

	public String getKothName() {
		return game.getKothName();
	}
}
