package me.enzosocks.xkoth_socks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class XScoreboard {
	protected Scoreboard scoreboard;

	public XScoreboard(String title, List<String> lines) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		Objective obj = scoreboard.registerNewObjective("koth", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(title);


		ChatColor[] colors = ChatColor.values();
		for (int i = 0; i < lines.size(); i++) {
			Team team = scoreboard.registerNewTeam(i + "");
			team.addEntry(colors[i].toString());
			team.setPrefix(colors[i].toString());
			team.setSuffix(ChatColor.RESET + lines.get(i));

			obj.getScore(colors[i].toString()).setScore(lines.size() - i);
		}
	}

	public void setTitle(String title) {
		Objective obj = scoreboard.getObjective("koth");
		obj.setDisplayName(title);
	}

	public void setLine(int i, String string) {
		Team team = scoreboard.getTeam(i + "");
		if (team != null)
			team.setSuffix(ChatColor.RESET + string);
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}
}
