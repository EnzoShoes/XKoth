package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothInfoCommand extends KothSubCommand {

	public KothInfoCommand(XKoth plugin) {
		super(plugin, "info");
	}

	public void run(CommandSender sender, String[] args) {
		Koth koth = plugin.getKothManager().getKoth(args[1]);

		MessageUtil.sendMessage(sender, "§cKoth info:");
		MessageUtil.sendMessage(sender, "§a- Name: " + koth.getName());
		MessageUtil.sendMessage(sender, "§a- World: " + koth.getCuboid().getWorld().getName());
		MessageUtil.sendMessage(sender, "§a- Location: " + koth.getCuboid().getLowerNE().toVector());
		MessageUtil.sendMessage(sender, "§a- Start times: " + koth.getStartTimes());
	}
}
