package me.enzosocks.xkoth_socks.instance.koth;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameStatus;
import me.enzosocks.xkoth_socks.managers.KothSettings;
import me.enzosocks.xkoth_socks.schedulers.Countdown;
import me.enzosocks.xkoth_socks.utils.Cuboid;

import java.time.LocalTime;
import java.util.List;

public class Koth {
	private KothSettings settings;
	private String name;
	private Cuboid cuboid;
	private int pointsToWin;
	private List<String> commandsOnWin;
	private Game game;
	private KothSchedule kothSchedule;
	private Countdown countdown;

	public Koth(String name, Cuboid cuboid, List<LocalTime> startTimes, int pointsToWin, List<String> commandsOnWin) {
		this.name = name;
		this.cuboid = cuboid;
		this.kothSchedule = new KothSchedule(startTimes);
		this.pointsToWin = pointsToWin;
		this.commandsOnWin = commandsOnWin;
		this.game = new Game(cuboid, pointsToWin, commandsOnWin);
		this.countdown = new Countdown(this);
	}

	public Koth(String name, Cuboid cuboid, KothSettings settings, KothSchedule schedule) {
		this.name = name;
		this.settings = settings;
		this.kothSchedule = schedule;
		this.game = new Game(settings.getCuboid(), settings.getPointsToWin(), settings.getCommandsOnWin());
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

	public Cuboid getCuboid() {
		return cuboid;
	}

	public int getPointsToWin() {
		return pointsToWin;
	}

	public List<String> getCommandsOnWin() {
		return commandsOnWin;
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

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
}
