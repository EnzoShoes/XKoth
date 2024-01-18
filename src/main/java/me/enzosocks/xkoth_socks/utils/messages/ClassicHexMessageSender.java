package me.enzosocks.xkoth_socks.utils.messages;

import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassicHexMessageSender implements MessageSender {
	@Override
	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(colorize(message));
	}

	private String colorize(String message) {
		if (message == null) return null;
		Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
		Matcher matcher = pattern.matcher(message);
		while (matcher.find()) {
			String color = message.substring(matcher.start(), matcher.end());
			message = message.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color)));
			matcher = pattern.matcher(message);
		}
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}
}
