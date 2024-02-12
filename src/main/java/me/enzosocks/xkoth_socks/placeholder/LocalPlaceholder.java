package me.enzosocks.xkoth_socks.placeholder;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.managers.KothManager;
import me.enzosocks.xkoth_socks.utils.Logger;
import me.enzosocks.xkoth_socks.utils.TimeUtil;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocalPlaceholder {

	private final String prefix = "xkoth";
	//Placeholder pattern (ex: %player_name%)
	private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");
	private final List<AutoPlaceholder> autoPlaceholders = new ArrayList<>();
	private KothManager manager;

	/**
	 * static Singleton instance.
	 */
	private static volatile LocalPlaceholder instance;

	/**
	 * Private constructor for singleton.
	 */
	public LocalPlaceholder(KothManager manager) {
		this.manager = manager;
		instance = this;
	}

	/**
	 * Targets individual %placeholders% and replaces them
	 *
	 * @param player
	 * @param placeholder
	 * @return replaced string
	 */
	public String replacePlaceholders(Player player, String placeholder) {
		if (placeholder == null || !placeholder.contains("%")) {
			return placeholder;
		}

		final String realPrefix = this.prefix + "_";

		Matcher matcher = this.pattern.matcher(placeholder);
		while (matcher.find()) {
			String stringPlaceholder = matcher.group(0);
			String regex = matcher.group(1).replace(realPrefix, "");
			String replace = this.onRequest(player, regex);
			if (replace != null) {
				placeholder = placeholder.replace(stringPlaceholder, replace);
			}
		}

		return placeholder;
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
		return this.onRequest(null, placeholder);
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
	public String onRequest(Player player, String withoutPrefix) {
		String kothName = withoutPrefix.split("_")[0];
		Koth koth = manager.getKoth(kothName);

		if (koth == null) {
			Logger.warning("Placeholder parsing: Koth " + kothName + " not found");
			return null;
		}

		String placeHolder = withoutPrefix.split("_")[1];

		return this.onRequestKoth(player, koth, placeHolder);
	}

	public String onRequestKoth(Player player, Koth koth, String placeHolder) {
		if (placeHolder.equalsIgnoreCase("name"))
			return koth.getDisplayName();

		if (placeHolder.equalsIgnoreCase("x"))
			return String.valueOf((int) koth.getCuboid().getCenter().getX());

		if (placeHolder.equalsIgnoreCase("y"))
			return String.valueOf((int) koth.getCuboid().getCenter().getY());

		if (placeHolder.equalsIgnoreCase("z"))
			return String.valueOf((int) koth.getCuboid().getCenter().getZ());

		if (placeHolder.equalsIgnoreCase("world"))
			return koth.getCuboid().getCenter().getWorld().getName();

		if (placeHolder.equalsIgnoreCase("scorePlayer1"))
			return String.valueOf(koth.getGame().getScoreTracker().getPointsForPosition(1));

		if (placeHolder.equalsIgnoreCase("scorePlayer2"))
			return String.valueOf(koth.getGame().getScoreTracker().getPointsForPosition(1));

		if (placeHolder.equalsIgnoreCase("scorePlayer3"))
			return String.valueOf(koth.getGame().getScoreTracker().getPointsForPosition(1));

		if (placeHolder.equalsIgnoreCase("score"))
			return String.valueOf(koth.getGame().getScoreTracker().getPoints(player.getUniqueId()));

		if (placeHolder.equalsIgnoreCase("countdown"))
			return TimeUtil.formatTime(Duration.between(LocalTime.now(), koth.getCountdown().getNextStartTime()).getSeconds());

		if (placeHolder.equalsIgnoreCase("timeLeft"))
			return TimeUtil.formatTime(koth.getGame().getGameLoop().getTimeLeft());

		if (placeHolder.equalsIgnoreCase("currentCapturer"))
			return koth.getGame().getGameLoop().getCapper() == null ? "None" : koth.getGame().getGameLoop().getCapper().getName();

		return null;
	}

	public String getPrefix() {
		return prefix;
	}

	public static LocalPlaceholder getInstance() {
		return instance;
	}

}