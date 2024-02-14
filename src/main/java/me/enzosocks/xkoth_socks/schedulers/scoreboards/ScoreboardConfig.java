package me.enzosocks.xkoth_socks.schedulers.scoreboards;

import java.util.List;

public interface ScoreboardConfig {
	boolean isOnlyShowWhenCapturing();

	int getViewDistance();

	String getTitle();

	List<String> getLines();
}
