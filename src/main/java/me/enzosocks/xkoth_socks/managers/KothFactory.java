package me.enzosocks.xkoth_socks.managers;

import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.game.scoreTracker.ScoreTracker;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.schedulers.ScoreGameLoop;
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
		Game game = new Game(name, new GameRules(), new ArrayList<>()); // default game

		//TODO: replace with factory
		GameLoop gameLoop = new ScoreGameLoop(game, cuboid, new ScoreTracker());
		game.setGameLoop(gameLoop);

		KothSchedule schedule = new KothSchedule(); // default schedule
		return new Koth(name, name, game, schedule);
	}
}
