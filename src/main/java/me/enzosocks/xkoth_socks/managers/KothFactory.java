package me.enzosocks.xkoth_socks.managers;

import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.utils.Cuboid;

import java.util.ArrayList;

public class KothFactory {
	public Koth createKoth(String name, Region region) throws IllegalArgumentException {
		Cuboid cuboid = Cuboid.fromRegion(region);
		if (cuboid == null) {
			throw new IllegalArgumentException("Invalid region");
		}
		return createKoth(name, cuboid);
	}

	public Koth createKoth(String name, Cuboid cuboid) {
		//TODO: replace ArrayList with Commands class that has default commands
		Game game = new Game(name, cuboid, new GameRules(), new ArrayList<>()); // default game
		KothSchedule schedule = new KothSchedule(); // default schedule
		return new Koth(name, name, game, schedule);
	}
}
