package me.enzosocks.xkoth_socks.commands.koth;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.Region;
import me.enzosocks.xkoth_socks.ErrorMessage;
import me.enzosocks.xkoth_socks.SubCommand;
import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.utils.messages.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KothCreateCommand extends SubCommand {

	private XKoth plugin;

	public KothCreateCommand(XKoth plugin) {
		super("create");
		this.plugin = plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length != 2) {
			MessageUtil.sendMessage(sender, "§cUsage: /koth create <koth_name>");
			return true;
		}
		try {
			Actor player = BukkitAdapter.adapt((Player) sender);
			LocalSession session = WorldEdit.getInstance().getSessionManager().get(player);
			if (session != null) {
				Region region = session.getSelection(session.getSelectionWorld());
				ErrorMessage result = plugin.getKothManager().createKoth(args[1], region);
				if (result == ErrorMessage.SUCCESS) {
					MessageUtil.sendMessage(sender, "§aKoth created !");
					return true;
				}
				MessageUtil.sendMessage(sender, "§c" + result.getMessage());
			} else {
				MessageUtil.sendMessage(sender, "§cYou must make a selection first !");
			}
		} catch (NullPointerException | IncompleteRegionException e) {
			MessageUtil.sendMessage(sender, "§cYou must make a selection first !");
		}
		return true;
	}
}
