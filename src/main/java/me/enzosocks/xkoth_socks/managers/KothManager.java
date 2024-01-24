package me.enzosocks.xkoth_socks.managers;

import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.ErrorMessage;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.Cuboid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KothManager {
	private XKoth plugin;
	private List<Koth> koths;

	public KothManager(XKoth plugin) {
		this.plugin = plugin;
		koths = plugin.getConfigManager().getKoths();
	}

	public boolean startKoth(String kothName) {
		Koth koth = getKoth(kothName);
		if (koth == null)
			return false;
		return koth.start();
	}

	public boolean stopKoth(String kothName) {
		Koth koth = getKoth(kothName);
		if (koth == null)
			return false;
		return koth.stop();
	}

	public Koth getKoth(String kothName) {
		for (Koth koth : koths) {
			if (koth.getName().equalsIgnoreCase(kothName))
				return koth;
		}
		return null;
	}

	public List<Koth> getKoths() {
		return koths;
	}

	public ErrorMessage createKoth(String kothName, Region region) {
		if (getKoth(kothName) != null)
			return ErrorMessage.KOTH_ALREADY_EXISTS;
		Cuboid cuboid = Cuboid.fromRegion(region);
		if (cuboid == null)
			return ErrorMessage.INVALID_REGION;

		Koth koth = new Koth(kothName, cuboid, new ArrayList<>(), 100, Collections.singletonList("give %player% diamond 1"));
		koths.add(koth);

		plugin.getConfigManager().saveKoths(koths);
		return ErrorMessage.SUCCESS;
	}

	public ErrorMessage deleteKoth(String kothName) {
		Koth koth = getKoth(kothName);
		if (koth == null)
			return ErrorMessage.KOTH_NOT_FOUND;
		koths.remove(koth);
		plugin.getConfigManager().saveKoths(koths);
		return ErrorMessage.SUCCESS;
	}
}
