package me.enzosocks.xkoth_socks.instance.koth;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.game.GameStatus;
import me.enzosocks.xkoth_socks.schedulers.Countdown;
import me.enzosocks.xkoth_socks.utils.Cuboid;

import java.time.LocalTime;
import java.util.List;

public class Koth {
	private String name;
	private Game game;
	private KothSchedule kothSchedule;
	private Countdown countdown;

	public Koth(String name, Game game, KothSchedule schedule) {
		this.name = name;
		this.kothSchedule = schedule;
		this.game = game;
		this.countdown = new Countdown(this);
	}

	public boolean start() {
		if (game.getStatus() == GameStatus.RUNNING) {
			System.out.println("Game is already running.");
			return false;
		}

		game.start();
		return true;
	}

	public boolean stop() {
		if (game.getStatus() == GameStatus.STOPPED)
			return false;

		game.stop(true);
		return true;
	}

	public String getName() {
		return name;
	}

	// TODO: code smell ? a lot of delegating

	public Cuboid getCuboid() {
		return game.getCuboid();
	}

	public GameRules getRules() {
		return game.getRules();
	}

	public List<String> getCommandsOnWin() {
		return game.getCommandsOnWin();
	}

	public Countdown getCountdown() {
		return countdown;
	}

	public Game getGame() {
		return game;
	}

	public List<LocalTime> getStartTimes() {
		return kothSchedule.getStartTimes();
	}

	public long getNextStartTime() {
		return kothSchedule.getNextStartTime();
	}
}
