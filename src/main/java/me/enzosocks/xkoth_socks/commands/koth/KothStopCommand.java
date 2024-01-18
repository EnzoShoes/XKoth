package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import me.enzosocks.xkoth_socks.instance.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothStopCommand extends KothSubCommand {

	public KothStopCommand(XKoth plugin) {
		super(plugin, "stop");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		Koth koth = plugin.getKothManager().getKoth(args[1]);
		if (koth == null) {
			MessageUtil.sendMessage(sender, "§cKoth not found !");
			return;
		}
		if (!koth.stop()) {
			MessageUtil.sendMessage(sender, "§cKoth is already stopped !");
		}
	}
}
