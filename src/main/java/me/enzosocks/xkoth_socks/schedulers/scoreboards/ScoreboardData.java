package me.enzosocks.xkoth_socks.schedulers.scoreboards;

import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.entity.Player;

public interface ScoreboardData {
	Player getCapper();

	String getKothName();

	Cuboid getCuboid();
}
