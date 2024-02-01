package me.enzosocks.xkoth_socks.instance.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ScoreTracker {
	private final Map<UUID, Integer> points = new HashMap<>();

	public void addPoints(UUID playerId, int pointsToAdd) {
		int currentPoints = points.getOrDefault(playerId, 0);
		points.put(playerId, currentPoints + pointsToAdd);
	}

	public void removePoints(UUID playerId, int pointsToRemove) {
		int currentPoints = points.getOrDefault(playerId, 0);
		points.put(playerId, Math.max(0, currentPoints - pointsToRemove));
	}

	public String toString() {
		return points.toString();
	}

	public void clear() {
		points.clear();
	}

	public int getPoints(UUID playerId) {
		return points.getOrDefault(playerId, 0);
	}

	public Optional<Map.Entry<UUID, Integer>> getHighestScore() {
		return this.points.entrySet().stream().max(Map.Entry.comparingByValue());
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}
}
