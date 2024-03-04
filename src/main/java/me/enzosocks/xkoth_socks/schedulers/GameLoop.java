package me.enzosocks.xkoth_socks.schedulers;

import me.enzosocks.xkoth_socks.instance.game.scoreTracker.IScoreTracker;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.entity.Player;

public interface GameLoop {
	void startLoop();

	void stopLoop();

	Cuboid getCuboid();

	long getTimeLeft();

	double getPercentageTimeLeft();

	Player getCapper();

	String getKothName();

	IScoreTracker getScoreTracker();
}
