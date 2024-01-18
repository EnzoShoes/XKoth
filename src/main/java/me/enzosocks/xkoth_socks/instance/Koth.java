package me.enzosocks.xkoth_socks.instance;

import me.enzosocks.xkoth_socks.utils.Cuboid;
import me.enzosocks.xkoth_socks.schedulers.Countdown;

import java.time.LocalTime;
import java.util.List;

public class Koth {
	private String name;
	private Cuboid cuboid;
	private int pointsToWin;
	private List<String> commandsOnWin;
	private Game game;
	private List<LocalTime> startTimes;
	private Countdown countdown;

	public Koth(String name, Cuboid cuboid, List<LocalTime> startTimes, int pointsToWin, List<String> commandsOnWin) {
		this.name = name;
		this.cuboid = cuboid;
		this.startTimes = startTimes;
		this.pointsToWin = pointsToWin;
		this.commandsOnWin = commandsOnWin;
		this.game = new Game(cuboid, pointsToWin, commandsOnWin);
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
		return startTimes;
	}

	public long getNextStartTime() {
		long currentTime = System.currentTimeMillis() / 1000;
		for (LocalTime startTime : startTimes) {
			long startTimeSeconds = startTime.getHour() * 3600 + startTime.getMinute() * 60 + startTime.getSecond();
			if (startTimeSeconds > currentTime)
				return startTimeSeconds;
		}
		return -1;
	}
}
