package me.enzosocks.xkoth_socks.utils;

import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {
	private static Logger logger;
	public String prefix;

	public Logger(String prefix) {
		this.prefix = prefix;
		logger = this;
	}

	public static void error(String message) {
		getLogger().log(LogLevel.ERROR, message);
	}

	public static void warning(String message) {
		getLogger().log(LogLevel.WARNING, message);
	}

	public static void info(String message) {
		getLogger().log(LogLevel.INFO, message);
	}

	public void log(LogLevel level, String message) {
		Bukkit.getConsoleSender().sendMessage(MessageUtil.colorize("&8[" + this.prefix + "&8]&r " + level.color + message));
	}

	public static Logger getLogger() {
		return logger;
	}

	public enum LogLevel {
		INFO("&f", Level.INFO), WARNING("&e", Level.WARNING), ERROR("&c", Level.SEVERE);

		private String color;
		private Level level;

		LogLevel(String color, Level level) {
			this.color = color;
			this.level = level;
		}
	}
}
