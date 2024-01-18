package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Koth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KothCommand implements CommandExecutor {

	XKoth plugin;

	public KothCommand(XKoth plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!label.equalsIgnoreCase("koth") && !label.equalsIgnoreCase("xkoth")) {
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage("§cWrong usage! Try /koth help for more info.");
			return false;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")){
				sender.sendMessage("§cKoths:");
				for (Koth koth : plugin.getKothManager().getKoths()) {
					sender.sendMessage("§a- " + koth.getName());
				}
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("§cKoth help:");
				sender.sendMessage("§a- /koth start <koth_name>");
				sender.sendMessage("§a- /koth stop <koth_name>");
				sender.sendMessage("§a- /koth info <koth_name>");
				sender.sendMessage("§a- /koth list");
				return true;
			} else {
				sender.sendMessage("§cWrong usage! Try /koth start <koth_name>");
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {
				boolean success = plugin.getKothManager().startKoth(args[1]);
				if (!success) {
					sender.sendMessage("§cKoth could not be started !");
					return true;
				}

				return true;
			} else if (args[0].equalsIgnoreCase("stop")) {
				boolean success = plugin.getKothManager().stopKoth(args[1]);
				if (!success) {
					sender.sendMessage("§cKoth could not be started !");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				Koth koth = plugin.getKothManager().getKoth(args[1]);
				if (koth == null) {
					sender.sendMessage("§cKoth not found !");
					return true;
				}

				sender.sendMessage("§cKoth info:");
				sender.sendMessage("§a- Name: " + koth.getName());
				sender.sendMessage("§a- World: " + koth.getCuboid().getWorld().getName());
				sender.sendMessage("§a- Points to win: " + koth.getPointsToWin());
				sender.sendMessage("§a- Start times: " + koth.getStartTimes());
			} else if (args[0].equalsIgnoreCase("create")) {

			} else {
				sender.sendMessage("§cWrong usage! Try /koth start");
				return true;
			}
		}

		return true;
	}
}
