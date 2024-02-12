package me.enzosocks.xkoth_socks.instance.koth;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.game.GameStatus;
import me.enzosocks.xkoth_socks.instance.game.StopReason;
import me.enzosocks.xkoth_socks.schedulers.Countdown;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import me.enzosocks.xkoth_socks.utils.Logger;

import java.time.LocalTime;
import java.util.List;

public class Koth {
	private String name;
	private String displayName;
	private Game game;
	private KothSchedule kothSchedule;
	private Countdown countdown;

	public Koth(String name, String displayName, Game game, KothSchedule schedule) {
		this.name = name;
		this.displayName = displayName;
		this.kothSchedule = schedule;
		this.game = game;
		this.countdown = new Countdown(this);
	}

	public boolean start() {
		if (game.getStatus() == GameStatus.RUNNING) {
			Logger.warning("Game is already running. Silently ignoring start request.");
			return false;
		}

		game.start();
		return true;
	}

	public boolean startIn(int seconds) {
		if (game.getStatus() == GameStatus.RUNNING) {
			Logger.warning("Game is already running. Silently ignoring start request.");
			return false;
		}

		countdown.setNextStartTime(LocalTime.now().plusSeconds(seconds));

		return true;
	}

	public boolean stop() {
		if (game.getStatus() == GameStatus.STOPPED)
			return false;

		game.stop(StopReason.FORCE_STOP);
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

	public LocalTime getNextStartTime() {
		return kothSchedule.getNextStartTime();
	}

	public void cancelCountdown() {
		countdown.cancel();
	}

	public String getDisplayName() {
		return displayName;
	}
}
