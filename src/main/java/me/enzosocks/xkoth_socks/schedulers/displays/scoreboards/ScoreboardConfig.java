package me.enzosocks.xkoth_socks.schedulers.displays.scoreboards;

import me.enzosocks.xkoth_socks.schedulers.displays.DisplayConfig;

import java.util.List;

public interface ScoreboardConfig extends DisplayConfig {
	String getTitle();

	List<String> getLines();
}
