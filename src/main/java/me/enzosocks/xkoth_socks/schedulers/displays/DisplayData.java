package me.enzosocks.xkoth_socks.schedulers.displays;

import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.entity.Player;

public interface DisplayData {
	Player getCapper();

	String getKothName();

	Cuboid getCuboid();

	double getPercentageTimeLeft();
}
