package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.GameStatus;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/*
 * All countdown announcements and game start/end logic will be handled here.
 */
public class Countdown extends BukkitRunnable {

	private Koth koth;
	private LocalTime nextStartTime;
	private boolean cancelled;

	public Countdown(Koth koth) {
		this.koth = koth;
		nextStartTime = koth.getNextStartTime();
		start();
	}

	private void start() {
		runTaskTimer(XKoth.getInstance(), 0, 20);
	}

	@Override
	public void run() {
		if (koth.getGame().getStatus() == GameStatus.RUNNING || nextStartTime == null) {
			return;
		}

		LocalTime currentTime = LocalTime.now();

		if (!cancelled) {
			long timeRemaining = ChronoUnit.SECONDS.between(currentTime, nextStartTime);

			if (timeRemaining <= 60 && timeRemaining > 0 && timeRemaining % 15 == 0) {
				Bukkit.broadcastMessage("KOTH " + koth.getName() + " will start in " + timeRemaining + " seconds !");
			}
		}

		if (currentTime.getHour() == nextStartTime.getHour() && currentTime.getMinute() == nextStartTime.getMinute() && currentTime.getSecond() >= nextStartTime.getSecond()) {
			if (!cancelled)
				koth.start();

			nextStartTime = koth.getNextStartTime();
			cancelled = false;
		}
	}

	public void setNextStartTime(LocalTime nextStartTime) {
		cancelled = false;
		this.nextStartTime = nextStartTime;
	}

	public LocalTime getNextStartTime() {
		return nextStartTime;
	}

	public void cancel() {
		cancelled = true;
	}
}
