package me.enzosocks.xkoth_socks.instance.game.scoreTracker;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ScoreTracker implements IScoreTracker {
	private final Map<UUID, Integer> points = new HashMap<>();
	private UUID capper;

	@Override
	public void incrementPoints() {
		if (capper != null) {
			addPoints(capper, 1);
		}
	}

	@Override
	public int getPoints(OfflinePlayer player) {
		return points.getOrDefault(player.getUniqueId(), 0);
	}

	@Override
	public void clear() {
		points.clear();
	}

	@Override
	public Player getCapper() {
		if (capper != null)
			return Bukkit.getPlayer(capper);
		return null;
	}

	@Override
	public void changeCapper(UUID capper) {
		this.capper = capper;
	}

	public void addPoints(UUID playerId, int pointsToAdd) {
		int currentPoints = points.getOrDefault(playerId, 0);
		points.put(playerId, currentPoints + pointsToAdd);
	}

	public String toString() {
		return points.toString();
	}

	public Optional<Map.Entry<UUID, Integer>> getScoreAtPosition(int position) {
		return this.points.entrySet().stream().sorted((a, b) -> b.getValue() - a.getValue()).skip(position - 1).findFirst();
	}

	public Optional<OfflinePlayer> getPlayerForPosition(int position) {
		return getScoreAtPosition(position).map(entry -> Bukkit.getOfflinePlayer(entry.getKey()));
	}


	public Optional<Integer> getPointsForPosition(int position) {
		return getScoreAtPosition(position).map(Map.Entry::getValue);
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}
}
