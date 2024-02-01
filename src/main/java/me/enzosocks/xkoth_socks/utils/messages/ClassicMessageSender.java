package me.enzosocks.xkoth_socks.utils.messages;

import org.bukkit.command.CommandSender;

public class ClassicMessageSender implements MessageSender {
	@Override
	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(colorize(message));
	}

	public String colorize(String message) {
		if (message == null) return null;
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}
}
