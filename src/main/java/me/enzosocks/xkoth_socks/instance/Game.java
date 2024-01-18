package me.enzosocks.xkoth_socks.instance;

import me.enzosocks.xkoth_socks.Cuboid;
import me.enzosocks.xkoth_socks.GameStatus;
import me.enzosocks.xkoth_socks.ScoreTracker;
import me.enzosocks.xkoth_socks.schedulers.SimplePointCounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Game implements ScoreTracker {
	private SimplePointCounter pointCounter;
	private Map<UUID, Integer> points = new HashMap<>();
	private Cuboid cuboid;
	private List<String> commandsOnWin;
	private int pointsToWin;
	private GameStatus status = GameStatus.STOPPED;

	public Game(Cuboid cuboid, int pointsToWin, List<String> commandsOnWin) {
		this.pointsToWin = pointsToWin;
		this.commandsOnWin = commandsOnWin;
		this.pointCounter = new SimplePointCounter(this, cuboid, pointsToWin);
		this.cuboid = cuboid;
		this.commandsOnWin = commandsOnWin;
	}

	public void start() {
		this.pointCounter = new SimplePointCounter(this, cuboid, pointsToWin);
		System.out.println("Game started.");
		status = GameStatus.RUNNING;
		points.clear();
		Bukkit.broadcastMessage("Starting point counter.");
		this.pointCounter.start();
	}

	public void stop(boolean forced) {
		status = GameStatus.STOPPED;
		this.pointCounter.cancel();
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
		return this.points.entrySet().stream().max(Map.Entry.comparingByValue());
	}

	@Override
	public void addPoint(Player player, int amount) {
		if (this.points.containsKey(player.getUniqueId())) {
			this.points.put(player.getUniqueId(), this.points.get(player.getUniqueId()) + amount);
		} else {
			this.points.put(player.getUniqueId(), amount);
		}
	}

	@Override
	public void removePoint(Player player, int amount) {
		if (this.points.containsKey(player.getUniqueId())) {
			this.points.put(player.getUniqueId(), this.points.get(player.getUniqueId()) - amount);
		} else {
			this.points.put(player.getUniqueId(), amount);
		}
	}

	public BukkitRunnable getPointCounter() {
		return pointCounter;
	}

	public Map<UUID, Integer> getPoints() {
		return points;
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
