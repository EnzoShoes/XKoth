package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public abstract class KothSubCommand extends SubCommand {

	protected XKoth plugin;

	public KothSubCommand(XKoth plugin, String name) {
		super(name);
		this.plugin = plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length <= 1) {
			MessageUtil.sendMessage(sender, "&cUsage: " + getSyntax());
			return true;
		}

		if (plugin.getKothManager().getKoth(args[1]) == null) {
			MessageUtil.sendMessage(sender, "&cKoth not found !");
			return true;
		}

		run(sender, args);
		return true;
	}

	@Override
	public String getSyntax() {
		return "/koth " + getLabel() + " <koth_name>";
	}

	protected abstract void run(CommandSender sender, String[] args);
}
