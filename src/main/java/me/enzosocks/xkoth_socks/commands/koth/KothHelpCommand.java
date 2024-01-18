package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.SubCommand;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothHelpCommand extends SubCommand {
	private XKoth plugin;

	public KothHelpCommand(XKoth plugin) {
		super("help");
		this.plugin = plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		MessageUtil.sendMessage(sender, "§cKoth help:");
		MessageUtil.sendMessage(sender, "§a- /koth start <koth_name>");
		MessageUtil.sendMessage(sender, "§a- /koth stop <koth_name>");
		MessageUtil.sendMessage(sender, "§a- /koth info <koth_name>");
		MessageUtil.sendMessage(sender, "§a- /koth list");
		MessageUtil.sendMessage(sender, "§a- /koth version");
		return true;
	}
}
