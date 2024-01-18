package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.Loader;
import me.enzosocks.xkoth_socks.SubCommand;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.koth.*;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler implements Loader, CommandExecutor {
	private List<SubCommand> commands = new ArrayList<>();
	private final String LABEL = "koth";
	private final List<String> ALIASES = Arrays.asList("xkoth");
	private final SubCommand defaultCommand;
	private XKoth plugin;

	public CommandHandler(XKoth plugin) {
		this.plugin = plugin;
		defaultCommand = new KothHelpCommand(plugin);

		commands.add(new KothStartCommand(plugin));
		commands.add(new KothStopCommand(plugin));
		commands.add(new KothListCommand(plugin));
		commands.add(new KothInfoCommand(plugin));
		commands.add(new KothVersionCommand(plugin));
		commands.add(defaultCommand);
	}

	@Override
	public void load() {
		plugin.getCommand(LABEL).setExecutor(this);
	}

	@Override
	public void unload() {
		plugin.getCommand(LABEL).setExecutor(null);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!label.equalsIgnoreCase(LABEL) && ALIASES.stream().noneMatch(s -> s.equalsIgnoreCase(label))) {
			return true;
		}

		if (args.length == 0)
			return defaultCommand.execute(sender, args);

		for (SubCommand subCommand : commands) {
			if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
				return subCommand.execute(sender, args);
			}
		}

		MessageUtil.sendMessage(sender, "&cInvalid usage. Use /koth help for more info.");
		return true;
	}
}
