package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import org.bukkit.command.CommandSender;

public class KothDeleteCommand extends KothSubCommand {

	public KothDeleteCommand(XKoth plugin) {
		super(plugin, "delete");
	}

	@Override
	protected void run(CommandSender sender, String[] args) {
		plugin.getKothManager().deleteKoth(args[1]);
	}
}
