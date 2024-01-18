package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.GameStatus;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Koth;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * All countdown announcements and game start/end logic will be handled here.
 */
public class Countdown extends BukkitRunnable {

	private Koth koth;
	private long nextStartTime;
	private boolean countDownStarted = false;

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
		if (koth.getGame().getStatus() == GameStatus.RUNNING) {
			return;
		}

		long currentTime = System.currentTimeMillis() / 1000;
		long timeRemaining = nextStartTime - currentTime;

		if (timeRemaining < 60 && timeRemaining > 0 && timeRemaining % 15 == 0) {
			Bukkit.broadcastMessage("Announcement: " + timeRemaining + " seconds until the start of the next KOTH.");
		}

		if (currentTime == nextStartTime) {
			koth.start();
			nextStartTime = koth.getNextStartTime();
		}
	}

	public void setNextStartTime(long nextStartTime) {
		this.nextStartTime = nextStartTime;
	}
}
