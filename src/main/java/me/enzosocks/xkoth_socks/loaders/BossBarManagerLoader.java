package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.schedulers.displays.bossbars.BossBarConfig;
import me.enzosocks.xkoth_socks.schedulers.displays.bossbars.BossBarManager;
import me.enzosocks.xkoth_socks.schedulers.displays.bossbars.IBossBar;
import me.enzosocks.xkoth_socks.schedulers.displays.bossbars.TimeBossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.function.Function;

public class BossBarManagerLoader implements Loader<BossBarManager> {
	@Override
	public BossBarManager load(FileConfiguration config, String path, Object... args) {
		if (!config.contains(path + ".bossbar")) {
			return null;
		}

		// SCORE MODE
		Function<BossBarConfig, IBossBar> bossBarSupplier = TimeBossBar::new;

		String mode = config.getString(path + ".mode");
		if (mode != null && mode.equalsIgnoreCase("CAPTURE")) {
			throw new UnsupportedOperationException("Capture mode is not yet supported.");
		}
		
		return new BossBarManager(
				config.getBoolean(path + ".bossbar.enabled"),
				config.getBoolean(path + ".bossbar.only-show-when-capturing"),
				config.getInt(path + ".bossbar.view-distance", 0),
				config.getString(path + ".bossbar.title", "&c%name% KOTH &7| &6%currentCapturer% &7(%timeLeft%)"),
				BarStyle.valueOf(config.getString(path + ".bossbar.style", BarStyle.SEGMENTED_20.name())),
				BarColor.valueOf(config.getString(path + ".bossbar.color", BarColor.GREEN.name())),
				bossBarSupplier);
	}
}
