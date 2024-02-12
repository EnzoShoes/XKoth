package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.schedulers.bossbars.IBossBar;
import me.enzosocks.xkoth_socks.schedulers.bossbars.NoBossBar;
import me.enzosocks.xkoth_socks.schedulers.bossbars.TimeBossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

public class BossBarLoader implements Loader<IBossBar> {
	@Override
	public IBossBar load(FileConfiguration config, String path, Object... args) {
		if (!config.contains(path + ".bossbar")
				|| !config.getBoolean(path + ".bossbar.enabled")
				|| config.getString(path + ".mode") == null) {
			return new NoBossBar();
		}

		String mode = config.getString(path + ".mode");

		IBossBar returnMe;
		if (mode.equalsIgnoreCase("SCORE")) {
			returnMe = new TimeBossBar();
		} else if (mode.equalsIgnoreCase("CAPTURE")) {
			throw new UnsupportedOperationException("Capture mode is not yet supported.");
		} else {
			return new NoBossBar();
		}

		returnMe.setOnlyShowWhenCapturing(config.getBoolean(path + ".bossbar.only-show-when-capturing", false));
		returnMe.setViewDistance(config.getInt(path + ".bossbar.view-distance", 0));
		returnMe.setName(config.getString(path + ".bossbar.name", "&c%name% KOTH &7| &6%currentCapturer% &7(%timeLeft%)"));
		returnMe.setColor(BarColor.valueOf(config.getString(path + ".bossbar.color", BarColor.GREEN.name())));
		returnMe.setStyle(BarStyle.valueOf(config.getString(path + ".bossbar.style", BarStyle.SEGMENTED_20.name())));

		return returnMe;
	}
}
