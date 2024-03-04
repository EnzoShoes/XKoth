package me.enzosocks.xkoth_socks.commands;

import me.enzosocks.xkoth_socks.Loader;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandLoader implements Loader, TabExecutor {
    private final List<SubCommand> subcommands = new ArrayList<>();
    private final JavaPlugin plugin;
    private final String label;

    private SubCommand defaultCommand;

    public AbstractCommandLoader(JavaPlugin plugin, String label) {
        this.plugin = plugin;
        this.label = label;
    }

    protected void addSubCommand(SubCommand subCommand) {
        this.subcommands.add(subCommand);
    }

    public void setDefaultCommand(SubCommand defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    public List<SubCommand> getSubcommands() {
        return subcommands;
    }

    @Override
    public final void load() {
        this.plugin.getCommand(this.label).setExecutor(this);
    }

    @Override
    public final void unload() {
        // not needed for commands.
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            return defaultCommand.execute(sender, args);

        for (SubCommand subCommand : getSubcommands()) {
            if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                return subCommand.execute(sender, args);
            }
        }

        MessageUtil.sendMessage(sender, "&cInvalid usage. Use /koth help for more info.");
        return true;
    }

    @Nullable
    @Override
    public final List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // your tab completion logic
        return null;
    }
}
