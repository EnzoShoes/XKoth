package me.enzosocks.xkoth_socks.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	private String label;

	public SubCommand(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getSyntax() {
		return "/koth " + label;
	}

	public abstract boolean execute(CommandSender sender, String[] args);
}
