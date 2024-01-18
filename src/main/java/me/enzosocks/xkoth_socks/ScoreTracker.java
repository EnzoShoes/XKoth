package me.enzosocks.xkoth_socks;

import org.bukkit.entity.Player;

public interface ScoreTracker {
	public void addPoint(Player player, int amount);
	public void removePoint(Player player, int amount);
}
