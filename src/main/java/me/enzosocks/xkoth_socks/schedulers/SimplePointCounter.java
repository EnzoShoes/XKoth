package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimplePointCounter {
	private Game game;
	private Cuboid cuboid;
	private BukkitRunnable runnable;
	private Player capturer;

	public SimplePointCounter(Game game, Cuboid cuboid) {
		this.game = game;
		this.cuboid = cuboid;
	}

	public void startCounting() {
		runnable = new BukkitRunnable() {
			@Override
			public void run() {
				if (capturer == null || !cuboid.contains(capturer.getLocation())) {
					capturer = getPlayerInCuboid();
				}
				game.addPoint(capturer, 10);
				System.out.println("Points: " + game.getPoints());

				Optional<Map.Entry<UUID, Integer>> highestScore = game.getHighestScore();
				if (highestScore.isPresent() && highestScore.get().getValue() >= game.getRules().getPointsToWin()) {
					game.stop(false);
				}
			}
		};

		runnable.runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	public void stopCounting() {
		runnable.cancel();
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
}
