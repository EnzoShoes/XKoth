package me.enzosocks.xkoth_socks.schedulers.scoreboards;

import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.placeholder.LocalPlaceholder;
import me.enzosocks.xkoth_socks.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class KothScoreboard {
	private boolean enabled;
	private boolean onlyShowWhenCapturing;
	private int viewDistance;
	private String title;
	private List<String> lines;
	Scoreboard scoreboard;
	private Set<Player> playersSeeingScoreboard = new HashSet<>();

	public KothScoreboard(boolean enabled, boolean onlyShowWhenCapturing, int viewDistance, String title, List<String> lines) {
		this.enabled = enabled;
		this.onlyShowWhenCapturing = onlyShowWhenCapturing;
		this.viewDistance = viewDistance;
		this.title = title;
		this.lines = lines;

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

	public void updateScoreboard(Player capturer, int gameTime, Game game) {
		if (!enabled)
			return;
		for (int i = 0; i < lines.size(); i++) {
			Team team = scoreboard.getTeam(i + "");
			LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
			if (team != null)
				team.setSuffix(ChatColor.RESET + placeholder.setPlaceholders(game.getKothName(), lines.get(i)));
			System.out.println("line + " + i + ": " + team.getSuffix());
		}

		List<Player> playersToShowBar = getPlayersToShowBar(capturer, game.getCuboid());
		Bukkit.getOnlinePlayers().forEach(player -> {
			if (playersToShowBar.contains(player) && !playersSeeingScoreboard.contains(player)) {
				playersSeeingScoreboard.add(player);
				player.setScoreboard(scoreboard);
			} else if (playersSeeingScoreboard.contains(player) && !playersToShowBar.contains(player)) {
				playersSeeingScoreboard.remove(player);
				player.setScoreboard(null);
			}
		});
	}

	protected List<Player> getPlayersToShowBar(Player capturer, Cuboid cuboid) {
		if (onlyShowWhenCapturing)
			//TODO: When Player becomes refactored to handle teams, return actual team members here
			return capturer == null ? new ArrayList<>() : Arrays.asList(capturer);
		return getPlayersWithinViewDistance(cuboid);
	}

	protected List<Player> getPlayersWithinViewDistance(Cuboid cuboid) {
		if (viewDistance == -1)
			return Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(cuboid.getWorld())).collect(Collectors.toList());
		if (viewDistance == 0)
			return new ArrayList<>(Bukkit.getOnlinePlayers());
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> player.getLocation().distance(cuboid.getLowerNE()) <= this.viewDistance)
				.collect(Collectors.toList());
	}

	protected String formatPlaceholders(String stringToFormat, Game game, int gameTime, long maxTime) {
		String formattedTitle = stringToFormat;

		Location kothLocation = game.getCuboid().getCenter();
		formattedTitle = formattedTitle.replace("%x%", kothLocation.getX() + "");
		formattedTitle = formattedTitle.replace("%y%", kothLocation.getY() + "");
		formattedTitle = formattedTitle.replace("%z%", kothLocation.getZ() + "");
		formattedTitle = formattedTitle.replace("%world%", kothLocation.getWorld().getName());
		formattedTitle = formattedTitle.replace("%kothName%", game.getKothName());
		long remainingTimeInSeconds = maxTime - gameTime;
		String timeLeftFormatted = String.format("%02d:%02d", remainingTimeInSeconds / 60, remainingTimeInSeconds % 60);
		formattedTitle = formattedTitle.replace("%timeLeft%", timeLeftFormatted);

		return ChatColor.translateAlternateColorCodes('&', formattedTitle);
	}

	public void hideScoreboard() {
		playersSeeingScoreboard.forEach(player -> player.setScoreboard(null));
		playersSeeingScoreboard.clear();
	}
}
