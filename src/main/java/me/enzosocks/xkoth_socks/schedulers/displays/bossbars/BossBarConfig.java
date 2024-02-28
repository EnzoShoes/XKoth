package me.enzosocks.xkoth_socks.schedulers.displays.bossbars;

import me.enzosocks.xkoth_socks.schedulers.displays.DisplayConfig;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public interface BossBarConfig extends DisplayConfig {
	String getTitle();

	BarStyle getStyle();

	BarColor getColor();
}