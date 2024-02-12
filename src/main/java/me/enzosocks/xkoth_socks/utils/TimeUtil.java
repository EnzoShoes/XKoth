package me.enzosocks.xkoth_socks.utils;

public class TimeUtil {
	public static String formatTime(long seconds) {
		long absSeconds = Math.abs(seconds);
		return String.format(
				"%d:%02d:%02d",
				absSeconds / 3600,
				(absSeconds % 3600) / 60,
				absSeconds % 60);
	}
}
