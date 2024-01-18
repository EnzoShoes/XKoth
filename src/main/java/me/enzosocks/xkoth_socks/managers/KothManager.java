package me.enzosocks.xkoth_socks.managers;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Koth;

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
}
