package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.SubCommand;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class KothHelpCommand extends SubCommand {
	private XKoth plugin;
	private List<SubCommand> kothCommands;

	public KothHelpCommand(XKoth plugin, List<SubCommand> kothCommands) {
		super("help");
		this.plugin = plugin;
		this.kothCommands = kothCommands;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		MessageUtil.sendMessage(sender, "§cKoth help:");
		for (SubCommand subCommand : kothCommands) {
			MessageUtil.sendMessage(sender, "§a- " + subCommand.getSyntax());
		}
		return true;
	}
}
