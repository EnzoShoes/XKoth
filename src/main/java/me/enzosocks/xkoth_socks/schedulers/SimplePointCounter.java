package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.Cuboid;
import me.enzosocks.xkoth_socks.ScoreTracker;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimplePointCounter extends BukkitRunnable {

	private Game game;
	private Cuboid cuboid;
	private int pointsToWin;
	private BukkitRunnable runnable;

	public SimplePointCounter(Game game, Cuboid cuboid, int pointsToWin) {
		this.game = game;
		this.cuboid = cuboid;
		this.pointsToWin = pointsToWin;
	}

	public void startRunning() {
		runnable = new BukkitRunnable() {
			@Override
			public void run() {
				List<Player> players = getPlayersInCuboid();
				players.forEach(player -> game.addPoint(player, 10));
				System.out.println("Points: " + game.getPoints());

				Optional<Map.Entry<UUID, Integer>> highestScore = game.getHighestScore();
				if (highestScore.isPresent() && highestScore.get().getValue() >= pointsToWin) {
					game.stop(false);
				}
			}
		};
		runnable.runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	public void start() {
		runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	@Override
	public void run() {
		List<Player> players = getPlayersInCuboid();
		players.forEach(player -> game.addPoint(player, 10));
		System.out.println("Points: " + game.getPoints());

		Optional<Map.Entry<UUID, Integer>> highestScore = game.getHighestScore();
		if (highestScore.isPresent() && highestScore.get().getValue() >= pointsToWin) {
			game.stop(false);
		}
	}

	private List<Player> getPlayersInCuboid() {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> cuboid.contains(player.getLocation()))
				.collect(Collectors.toList());
	}
}
