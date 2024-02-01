package me.enzosocks.xkoth_socks.commands.koth;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.KothSubCommand;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class KothCancelCommand extends KothSubCommand {
	public KothCancelCommand(XKoth plugin) {
		super(plugin, "cancel");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		Koth koth = plugin.getKothManager().getKoth(args[1]);
		if (koth == null) {
			MessageUtil.sendMessage(sender, "§cKoth not found !");
			return;
		}

		LocalTime currentTime = LocalTime.now();
		LocalTime nextStartTime = koth.getNextStartTime();

		if (nextStartTime != null) {
			long timeRemaining = ChronoUnit.SECONDS.between(currentTime, nextStartTime);

			if (timeRemaining < 60) {
				koth.cancelCountdown();
				MessageUtil.sendMessage(sender, "§aKoth has been cancelled");
				Bukkit.broadcastMessage("§cKoth " + koth.getName() + " has been cancelled by an administrator.");
			} else {
				MessageUtil.sendMessage(sender, "§cThe Koth is not about to start, so there is nothing to cancel.");
			}
		} else {
			MessageUtil.sendMessage(sender, "§cNo next start time found for the Koth.");
		}
	}
}
