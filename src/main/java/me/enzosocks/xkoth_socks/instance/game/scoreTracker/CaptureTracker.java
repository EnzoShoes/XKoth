package me.enzosocks.xkoth_socks.instance.game.scoreTracker;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CaptureTracker implements IScoreTracker {
	private UUID capper;
	private int points;

	@Override
	public void incrementPoints() {
		if (capper != null) {
			points++;
		}
	}

	@Override
	public int getPoints(OfflinePlayer offlinePlayer) {
		if (offlinePlayer != null && !offlinePlayer.getUniqueId().equals(capper)) {
			return 0;
		}
		return points;
	}

	@Override
	public void clear() {
		capper = null;
		points = 0;
	}

	@Override
	public Player getCapper() {
		if (capper == null) {
			return null;
		}
		return Bukkit.getPlayer(capper);
	}

	@Override
	public void changeCapper(UUID capper) {
		if (this.capper != null && this.capper.equals(capper))
			return;
		this.capper = capper;
		this.points = 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public UUID getTopPlayer() {
		return capper;
	}
}
