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

		if (args.length > 2) {
			if (args[2].equalsIgnoreCase("now")) {
				if (!koth.start()) {
					MessageUtil.sendMessage(sender, "§cKoth is already running !");
				}
				MessageUtil.sendMessage(sender, "§aKoth started !");
				return;
			}
		}

		if (!koth.startIn(61)) {
			MessageUtil.sendMessage(sender, "§cKoth is already running !");
		}
	}
}
