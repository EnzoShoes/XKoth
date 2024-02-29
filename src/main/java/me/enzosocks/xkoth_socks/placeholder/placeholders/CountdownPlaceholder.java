package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.TimeUtil;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalTime;

public class CountdownPlaceholder implements XPlaceholder {

	private static final String placeholder = "countdown";
	private final String defaultValue;

	public CountdownPlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return placeholder.equalsIgnoreCase(placeHolder);
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		if (koth.getCountdown() != null) {
			return TimeUtil.formatTime(Duration.between(LocalTime.now(), koth.getCountdown().getNextStartTime()).getSeconds());
		}
		return defaultValue;
	}
}
