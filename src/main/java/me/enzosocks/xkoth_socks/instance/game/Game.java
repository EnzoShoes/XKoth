package me.enzosocks.xkoth_socks.instance.game;

import me.enzosocks.xkoth_socks.schedulers.SimplePointCounter;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Game {
	private final SimplePointCounter pointCounter;
	private final Points points = new Points();
	private final Cuboid cuboid;
	private final List<String> commandsOnWin;
	private GameStatus status = GameStatus.STOPPED;

	public Game(Cuboid cuboid, int pointsToWin, List<String> commandsOnWin) {
		this.commandsOnWin = commandsOnWin;
		this.pointCounter = new SimplePointCounter(this, cuboid, pointsToWin);
		this.cuboid = cuboid;
	}

	public void start() {
		this.pointCounter.startCounting();
		BossBar bossbar = Bukkit.createBossBar("Koth", BarColor.RED, BarStyle.SOLID);
		bossbar.setProgress(1);
		System.out.println("Game started.");
		status = GameStatus.RUNNING;
		points.clear();
		Bukkit.broadcastMessage("Starting point counter.");
	}

	public void stop(boolean forced) {
		status = GameStatus.STOPPED;
		this.pointCounter.stopCounting();
		Optional<Map.Entry<UUID, Integer>> highestScore = getHighestScore();

		if (!highestScore.isPresent()) {
			Bukkit.broadcastMessage("Game has ended. No winner.");
			return;
		}

		String winnerName = Bukkit.getPlayer(highestScore.get().getKey()).getName();
		Bukkit.broadcastMessage("Game has ended. Winner is : " + winnerName);

		if (!forced) {
			commandsOnWin.forEach(command ->
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							command.replace("%player%", winnerName)));
		}
	}

	public Optional<Map.Entry<UUID, Integer>> getHighestScore() {
		return this.points.getHighestScore();
	}

	public void addPoint(Player player, int amount) {
		this.points.addPoints(player.getUniqueId(), amount);
	}

	public void removePoint(Player player, int amount) {
		this.points.addPoints(player.getUniqueId(), amount);
	}

	public SimplePointCounter getPointCounter() {
		return pointCounter;
	}

	public Points getPoints() {
		return this.points;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

}
