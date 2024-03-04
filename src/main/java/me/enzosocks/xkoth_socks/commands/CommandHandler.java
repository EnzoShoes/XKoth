package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.commands.koth.*;

public class CommandHandler extends AbstractCommandLoader {
	public CommandHandler(XKoth plugin) {
		super(plugin, "koth");

		addSubCommand(new KothStartCommand(plugin));
		addSubCommand(new KothStopCommand(plugin));
		addSubCommand(new KothCancelCommand(plugin));
		addSubCommand(new KothListCommand(plugin));
		addSubCommand(new KothInfoCommand(plugin));
		addSubCommand(new KothVersionCommand(plugin));
		addSubCommand(new KothCreateCommand(plugin));
		addSubCommand(new KothDeleteCommand(plugin));
		addSubCommand(new KothTimeCommand());

		final SubCommand helpCommand = new KothHelpCommand(plugin, getSubcommands());
		setDefaultCommand(helpCommand);
		addSubCommand(helpCommand);
	}
}
