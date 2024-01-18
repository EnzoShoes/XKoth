package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import me.enzosocks.xkoth_socks.instance.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothInfoCommand extends KothSubCommand {

	public KothInfoCommand(XKoth plugin) {
		super(plugin, "info");
	}

	public void run(CommandSender sender, String[] args) {
		Koth koth = plugin.getKothManager().getKoth(args[1]);
		if (koth == null) {
			MessageUtil.sendMessage(sender, "§cKoth not found !");
			return;
		}

		MessageUtil.sendMessage(sender, "§cKoth info:");
		MessageUtil.sendMessage(sender, "§a- Name: " + koth.getName());
		MessageUtil.sendMessage(sender, "§a- World: " + koth.getCuboid().getWorld().getName());
		MessageUtil.sendMessage(sender, "§a- Points to win: " + koth.getPointsToWin());
		MessageUtil.sendMessage(sender, "§a- Start times: " + koth.getStartTimes());
	}
}
