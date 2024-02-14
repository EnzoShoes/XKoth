package me.enzosocks.xkoth_socks.utils.messages;

import me.enzosocks.xkoth_socks.utils.NMSUtil;
import org.bukkit.command.CommandSender;

public class MessageUtil {

	private static MessageSender messageSender;
	private static final String PREFIX = "&8[&cXKoth&8] &7";

	static {
		if (NMSUtil.supportsHexColor()) {
			messageSender = new ClassicHexMessageSender();
		} else {
			messageSender = new ClassicMessageSender();
		}
	}

	public static void sendMessage(CommandSender sender, String message) {
		messageSender.sendMessage(sender, PREFIX + message);
	}

	public static String colorize(String message) {
		return messageSender.colorize(message);
	}
}
