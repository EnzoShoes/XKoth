package me.enzosocks.xkoth_socks.instance.game.scoreTracker;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IScoreTracker {
	/*
	 * Increment points according to current capper
	 */
	void incrementPoints();

	int getPoints(OfflinePlayer player);

	void clear();

	boolean isEmpty();

	Player getCapper();

	void changeCapper(UUID capper);

	UUID getTopPlayer();
}
