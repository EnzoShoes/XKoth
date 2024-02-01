package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.SubCommand;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothVersionCommand extends SubCommand {

	private XKoth plugin;

	public KothVersionCommand(XKoth plugin) {
		super("version");
		this.plugin = plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		MessageUtil.sendMessage(sender, "&cXKoth version info:");
		MessageUtil.sendMessage(sender, "&a- Version: " + plugin.getDescription().getVersion());
		MessageUtil.sendMessage(sender, "&a- Author: " + plugin.getDescription().getAuthors().get(0));
		MessageUtil.sendMessage(sender, "&a- Website: " + plugin.getDescription().getWebsite());
		return true;
	}
}
