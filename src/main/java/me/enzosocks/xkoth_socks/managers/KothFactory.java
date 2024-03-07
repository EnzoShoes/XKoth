package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.game.GameRules;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.schedulers.GameLoop;
import me.enzosocks.xkoth_socks.utils.Cuboid;

import java.util.ArrayList;

public class KothFactory {

	private XKoth plugin;

	public KothFactory(XKoth plugin) {
		this.plugin = plugin;
	}

	public Koth createKoth(String name, Cuboid cuboid) {
		Game game = new Game(plugin, name, new GameRules(), new ArrayList<>()); // default game

		GameLoop gameLoop = GameLoopFactory.createGameLoop(game, cuboid);
		game.setGameLoop(gameLoop);

		KothSchedule schedule = new KothSchedule(); // default schedule
		return new Koth(name, name, game, schedule);
	}
}
