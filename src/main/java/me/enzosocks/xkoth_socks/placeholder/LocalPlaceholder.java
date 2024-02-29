package me.enzosocks.xkoth_socks.placeholder;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.managers.KothManager;
import me.enzosocks.xkoth_socks.placeholder.placeholders.*;
import me.enzosocks.xkoth_socks.utils.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocalPlaceholder {

	private final String prefix = "xkoth";
	//Placeholder pattern (ex: %player_name%)
	private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");
	private final List<XPlaceholder> placeholders;
	private KothManager manager;
	private XKoth plugin;

	/**
	 * static Singleton instance.
	 */
	private static volatile LocalPlaceholder instance;

	/**
	 * Private constructor for singleton.
	 */
	public LocalPlaceholder(XKoth plugin) {
		this.plugin = plugin;
		this.manager = plugin.getKothManager();

		placeholders = Arrays.asList(
				new ScorePlayerPlaceholder("No one"),
				new ScorePointsPlaceholder("0"),
				new CoordinatePlaceholder("Coordinate not set"),
				new CountdownPlaceholder("Koth is not scheduled"),
				new TimeLeftPlaceholder("No ongoing game"),
				new ScorePlaceholder("0"),
				new SimplePlaceholder("name", Koth::getDisplayName),
				new SimplePlaceholder("world", koth -> koth.getCuboid().getCenter().getWorld().getName(), "Location not set"),
				new SimplePlaceholder("currentCapturer", koth -> koth.getGame().getGameLoop().getCapper().getName(), "No one is capping")
		);

		instance = this;
	}

	public static String replacePlaceholders(Player player, String placeholder, String kothName) {
		return getInstance().setPlaceholders(player, placeholder, kothName);
	}

	/**
	 * @param player
	 * @param placeholder
	 * @return replaced string
	 */
	public String setPlaceholders(Player player, String placeholder, String kothName) {

		if (placeholder == null || !placeholder.contains("%")) {
			return placeholder;
		}

		final String realPrefix = this.prefix + "_";

		Matcher matcher = this.pattern.matcher(placeholder);
		while (matcher.find()) {
			String stringPlaceholder = matcher.group(0);
			String regex = matcher.group(1).replace(realPrefix, "");

			if (kothName != null && !kothName.isEmpty()) {
				regex = kothName + "_" + regex;
			}

			String replace = this.onRequest(player, regex);
			if (replace != null) {
				placeholder = placeholder.replace(stringPlaceholder, replace);
			}
		}

		return placeholder;
	}

	public String setPlaceholders(Player player, String placeholder) {
		return setPlaceholders(player, placeholder, null);
	}

	public String setPlaceholders(String kothName, String placeholder) {
		return setPlaceholders(null, placeholder, kothName);
	}

	/**
	 * @param player
	 * @param lore
	 * @return
	 */
	public List<String> setPlaceholders(Player player, List<String> lore) {
		return lore == null ? null
				: lore.stream().map(e -> e = setPlaceholders(player, e)).collect(Collectors.toList());
	}

	/**
	 * Custom placeholder
	 *
	 * @param player
	 * @param withoutPrefix
	 * @return
	 */
	public String onRequest(OfflinePlayer player, String withoutPrefix) {
		String kothName = withoutPrefix.split("_")[0];
		Koth koth = manager.getKoth(kothName);

		if (koth == null) {
			Logger.warning("Placeholder parsing: Koth " + kothName + " not found");
			return null;
		}

		String placeHolder = withoutPrefix.split("_")[1];

		return this.onRequestKoth(player, koth, placeHolder);
	}

	public String onRequestKoth(OfflinePlayer player, Koth koth, String placeHolder) {
		for (XPlaceholder placeholder : placeholders) {
			if (placeholder.matches(placeHolder)) {
				return placeholder.parse(placeHolder, koth, player);
			}
		}

		return null;
	}

	public String getPrefix() {
		return prefix;
	}

	public XKoth getPlugin() {
		return plugin;
	}

	public static LocalPlaceholder getInstance() {
		return instance;
	}

}