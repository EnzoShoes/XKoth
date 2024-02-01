package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.SubCommand;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

public class KothListCommand extends SubCommand {

	private XKoth plugin;

	public KothListCommand(XKoth plugin) {
		super("list");
		this.plugin = plugin;
	}


	@Override
	public boolean execute(CommandSender sender, String[] args) {
		MessageUtil.sendMessage(sender, "§cKoths:");
		for (Koth koth : plugin.getKothManager().getKoths()) {
			MessageUtil.sendMessage(sender, "§a- " + koth.getName());
		}
		return true;
	}
}
