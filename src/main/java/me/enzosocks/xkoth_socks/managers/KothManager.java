package me.enzosocks.xkoth_socks.managers;

import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.Savable;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.enums.ErrorMessage;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.loaders.KothLoader;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import me.enzosocks.xkoth_socks.utils.Persist;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KothManager implements Savable {
	private XKoth plugin;
	private List<Koth> koths = new ArrayList<>();
	private final KothFactory kothFactory;

	public KothManager(XKoth plugin) {
		this.plugin = plugin;
		this.kothFactory = new KothFactory(plugin);
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

		Koth koth = kothFactory.createKoth(kothName, cuboid);
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

	@Override
	public void save(Persist persist) {
		FileConfiguration config = plugin.getConfig();
		for (Koth koth : koths) {
			config.set("koths." + koth.getName() + ".world", koth.getCuboid().getWorld().getName());
			config.set("koths." + koth.getName() + ".corner1.x", koth.getCuboid().getLowerNE().getX());
			config.set("koths." + koth.getName() + ".corner1.y", koth.getCuboid().getLowerNE().getY());
			config.set("koths." + koth.getName() + ".corner1.z", koth.getCuboid().getLowerNE().getZ());
			config.set("koths." + koth.getName() + ".corner2.x", koth.getCuboid().getUpperSW().getX());
			config.set("koths." + koth.getName() + ".corner2.y", koth.getCuboid().getUpperSW().getY());
			config.set("koths." + koth.getName() + ".corner2.z", koth.getCuboid().getUpperSW().getZ());
			List<String> startTimes = koth.getStartTimes().stream()
					.map(time -> String.format("%02d:%02d", time.getHour(), time.getMinute()))
					.collect(Collectors.toList());
			config.set("koths." + koth.getName() + ".kothTimes", startTimes);
			config.set("koths." + koth.getName() + ".commandsOnWin", koth.getCommandsOnWin());

			ConfigurationSection rules = config.createSection("koths." + koth.getName() + ".rules");
			rules.set("pointsToWin", koth.getRules().getPointsToWin());
			rules.set("maxTime", koth.getRules().getPointsToWin());
			rules.set("winerIfTimeRunsOut", koth.getRules().getPointsToWin());
		}
		plugin.saveConfig();
	}

	@Override
	public void load(Persist persist) {
		this.koths = loadKoths();
	}

	private List<Koth> loadKoths() {
		List<Koth> koths = new ArrayList<>();
		KothLoader kothLoader = new KothLoader(plugin);
		for (String kothName : plugin.getConfig().getConfigurationSection("koths").getKeys(false)) {
			Koth koth = kothLoader.load(plugin.getConfig(), "koths." + kothName, kothName);
			if (koth != null)
				koths.add(koth);
		}
		return koths;
	}
}
