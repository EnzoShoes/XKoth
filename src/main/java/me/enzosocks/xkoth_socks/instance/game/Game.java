package me.enzosocks.xkoth_socks.instance.game;

import me.enzosocks.xkoth_socks.instance.game.scoreTracker.IScoreTracker;
import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;

import java.util.List;

//TODO: code smell ? Large class
public class Game {
	public GameRules rules;
	private GameLoop gameLoop;
	private final List<String> commandsOnWin;
	private GameStatus status = GameStatus.STOPPED;
	private final String kothName;

	public Game(String kothName, GameRules rules, List<String> commandsOnWin) {
		this.commandsOnWin = commandsOnWin;
		this.rules = rules;
		this.kothName = kothName;
	}

	public void start() {
		this.gameLoop.startLoop();
		System.out.println("Game started.");
		status = GameStatus.RUNNING;
		Bukkit.broadcastMessage("Starting point counter.");
	}

	public void stop(StopReason reason) {
		status = GameStatus.STOPPED;

		Bukkit.broadcastMessage(LocalPlaceholder.replacePlaceholders(null, reason.getMessage(), kothName));
		if (reason.hasWinner) {
			commandsOnWin.forEach(command -> {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						LocalPlaceholder.replacePlaceholders(null, command, kothName));
			});
		}

		// Stop all timers
		this.gameLoop.stopLoop();
	}

	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public IScoreTracker getScoreTracker() {
		return gameLoop.getScoreTracker();
	}

	public Cuboid getCuboid() {
		return gameLoop.getCuboid();
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

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}
}
