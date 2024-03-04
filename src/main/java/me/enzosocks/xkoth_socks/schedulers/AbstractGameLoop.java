package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.scoreTracker.IScoreTracker;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class AbstractGameLoop implements GameLoop {
	protected final IScoreTracker scoreTracker;
	protected Game game;
	protected Cuboid cuboid;
	protected int gameTime = 0; // time elapsed since beginning of the game

	public AbstractGameLoop(Game game, Cuboid cuboid, IScoreTracker scoreTracker) {
		this.game = game;
		this.cuboid = cuboid;
		this.scoreTracker = scoreTracker;
	}

	@Override
	public abstract void startLoop();

	@Override
	public abstract void stopLoop();

	protected void countPoints() {
		if (getCapper() == null || !cuboid.contains(getCapper().getLocation())) {
			if (getPlayerInCuboid() == null)
				scoreTracker.changeCapper(null);
			else
				scoreTracker.changeCapper(getPlayerInCuboid().getUniqueId());
		}
		scoreTracker.incrementPoints();
	}

	private Player getPlayerInCuboid() {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> cuboid.contains(player.getLocation()))
				.findFirst().orElse(null);
	}

	@Override
	public Cuboid getCuboid() {
		return cuboid;
	}

	@Override
	public long getTimeLeft() {
		return game.getRules().getMaxTime() - gameTime;
	}

	@Override
	public double getPercentageTimeLeft() {
		return 1D - (double) gameTime / game.getRules().getMaxTime();
	}

	@Override
	public Player getCapper() {
		return scoreTracker.getCapper();
	}

	@Override
	public String getKothName() {
		return game.getKothName();
	}

	@Override
	public IScoreTracker getScoreTracker() {
		return scoreTracker;
	}
}
