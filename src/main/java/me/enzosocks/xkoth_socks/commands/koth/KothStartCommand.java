package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothStartCommand extends KothSubCommand {

	public KothStartCommand(XKoth plugin) {
		super(plugin, "start");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		Koth koth = plugin.getKothManager().getKoth(args[1]);
		if (koth == null) {
			MessageUtil.sendMessage(sender, "§cKoth not found !");
			return;
		}
		if (!koth.start()) {
			MessageUtil.sendMessage(sender, "§cKoth is already running !");
		}
	}
}
