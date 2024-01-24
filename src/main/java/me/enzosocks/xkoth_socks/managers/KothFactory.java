package me.enzosocks.xkoth_socks.managers;

import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import me.enzosocks.xkoth_socks.utils.Cuboid;

public class KothFactory {
	public Koth createKoth(String name, Region region) throws IllegalArgumentException {
		Cuboid cuboid = Cuboid.fromRegion(region);
		if (cuboid == null) {
			throw new IllegalArgumentException("Invalid region");
		}
		return createKoth(name, cuboid);
	}

	public Koth createKoth(String name, Cuboid cuboid) {
		KothSettings settings = new KothSettings(); // default settings
		KothSchedule schedule = new KothSchedule(); // default schedule
		//return new Koth(name, cuboid, new ArrayList<>(), 100, Collections.singletonList("give %player% diamond 1"));
		return new Koth(name, cuboid, settings, schedule); //game can be created inside koth
	}
}
