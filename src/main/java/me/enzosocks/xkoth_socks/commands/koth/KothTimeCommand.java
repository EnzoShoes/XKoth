package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.commands.SubCommand;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class KothTimeCommand extends SubCommand {
	public KothTimeCommand() {
		super("gettime");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		MessageUtil.sendMessage(sender, "&cCurrent time: " + time.format(formatter));
		return true;
	}
}
