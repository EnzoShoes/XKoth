package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KothCommand {// implements CommandExecutor {
	/*
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
			MessageUtil.sendMessage(sender,  "XKoth by EnzoSocks.");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")){
				MessageUtil.sendMessage(sender,  "§cKoths:");
				for (Koth koth : plugin.getKothManager().getKoths()) {
					MessageUtil.sendMessage(sender,  "§a- " + koth.getName());
				}
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				MessageUtil.sendMessage(sender,  "§cKoth help:");
				MessageUtil.sendMessage(sender,  "§a- /koth start <koth_name>");
				MessageUtil.sendMessage(sender,  "§a- /koth stop <koth_name>");
				MessageUtil.sendMessage(sender,  "§a- /koth info <koth_name>");
				MessageUtil.sendMessage(sender,  "§a- /koth list");
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {
				Koth koth = plugin.getKothManager().getKoth(args[1]);
				if (koth == null) {
					MessageUtil.sendMessage(sender,  "§cKoth not found !");
					return true;
				}
				if (!koth.start()) {
					MessageUtil.sendMessage(sender,  "§cKoth is already running !");
					return true;
				}

				return true;
			} else if (args[0].equalsIgnoreCase("stop")) {
				Koth koth = plugin.getKothManager().getKoth(args[1]);
				if (koth == null) {
					MessageUtil.sendMessage(sender,  "§cKoth not found !");
					return true;
				}
				if (!koth.stop()) {
					MessageUtil.sendMessage(sender,  "§cKoth is already stopped !");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				Koth koth = plugin.getKothManager().getKoth(args[1]);
				if (koth == null) {
					MessageUtil.sendMessage(sender,  "§cKoth not found !");
					return true;
				}

				MessageUtil.sendMessage(sender,  "§cKoth info:");
				MessageUtil.sendMessage(sender,  "§a- Name: " + koth.getName());
				MessageUtil.sendMessage(sender,  "§a- World: " + koth.getCuboid().getWorld().getName());
				MessageUtil.sendMessage(sender,  "§a- Points to win: " + koth.getPointsToWin());
				MessageUtil.sendMessage(sender,  "§a- Start times: " + koth.getStartTimes());
				return true;
			} else if (args[0].equalsIgnoreCase("create")) {

			}
		}

		MessageUtil.sendMessage(sender,  "§cWrong usage! Try /koth help for more info.");

		return true;
	}*/
}
