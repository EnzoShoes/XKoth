package me.enzosocks.xkoth_socks.instance.game;

import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//TODO: code smell ? Large class
public class Game {
	public GameRules rules;
	private final GameLoop pointCounter;
	private final ScoreTracker scoreTracker = new ScoreTracker();
	private final List<String> commandsOnWin;
	private GameStatus status = GameStatus.STOPPED;
	private String kothName;

	public Game(String kothName, Cuboid cuboid, GameRules rules, List<String> commandsOnWin) {
		this.commandsOnWin = commandsOnWin;
		this.rules = rules;
		this.pointCounter = new GameLoop(this, kothName, cuboid);
		this.kothName = kothName;
	}

	public void start() {
		this.pointCounter.startLoop();
		System.out.println("Game started.");
		status = GameStatus.RUNNING;
		scoreTracker.clear();
		Bukkit.broadcastMessage("Starting point counter.");
	}

	public void stop(StopReason reason) {
		stop(reason, "");
	}

	public void stop(StopReason reason, String winnerName) {
		status = GameStatus.STOPPED;
		// Stop all timers
		this.pointCounter.stopLoop();

		Bukkit.broadcastMessage(reason.getMessage().replace("%player%", winnerName));
		if (reason.hasWinner) {
			commandsOnWin.forEach(command ->
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							command.replace("%player%", winnerName)));
		}
	}

	public Optional<Map.Entry<UUID, Integer>> getHighestScore() {
		return this.scoreTracker.getHighestScore();
	}

	public void addPoint(Player player, int amount) {
		this.scoreTracker.addPoints(player.getUniqueId(), amount);
	}

	public void removePoint(Player player, int amount) {
		this.scoreTracker.addPoints(player.getUniqueId(), amount);
	}

	public GameLoop getPointCounter() {
		return pointCounter;
	}

	public ScoreTracker getPoints() {
		return this.scoreTracker;
	}

	public Cuboid getCuboid() {
		return pointCounter.getCuboid();
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GameRules getRules() {
		return rules;
	}

	public List<String> getCommandsOnWin() {
		return commandsOnWin;
	}

	public String getKothName() {
		return kothName;
	}
}
