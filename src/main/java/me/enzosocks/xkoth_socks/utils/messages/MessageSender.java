package me.enzosocks.xkoth_socks.utils.messages;

import org.bukkit.command.CommandSender;

public interface MessageSender {
	void sendMessage(CommandSender sender, String message);

	String colorize(String message);
}
