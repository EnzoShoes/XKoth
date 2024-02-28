package me.enzosocks.xkoth_socks.placeholder;

import me.enzosocks.xkoth_socks.XKoth;
import me.enzosocks.xkoth_socks.instance.game.Game;
import me.enzosocks.xkoth_socks.instance.koth.Koth;
import me.enzosocks.xkoth_socks.managers.KothManager;
import me.enzosocks.xkoth_socks.utils.Logger;
import me.enzosocks.xkoth_socks.utils.TimeUtil;
import me.enzosocks.xkoth_socks.utils.optionalparser.IntegerOptionalParser;
import me.enzosocks.xkoth_socks.utils.optionalparser.OptionalParser;
import me.enzosocks.xkoth_socks.utils.optionalparser.PlayerOptionalParser;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocalPlaceholder {

	private final Map<Predicate<String>, OptionalParser<?>> parsers = new HashMap<>();
	private final String prefix = "xkoth";
	//Placeholder pattern (ex: %player_name%)
	private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");
	private final List<AutoPlaceholder> autoPlaceholders = new ArrayList<>();
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

		parsers.put((str) -> str.startsWith("scorePlayer"), new PlayerOptionalParser());
		parsers.put((str) -> str.startsWith("scorePoints"), new IntegerOptionalParser());

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

	private <T> String parseOptional(Optional<T> optional, String placeHolder) {
		OptionalParser<T> parser = (OptionalParser<T>) parsers.entrySet()
				.stream()
				.filter(e -> e.getKey().test(placeHolder))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);

		if (parser != null) {
			return parser.parse(optional);
		}

		Logger.error("empty");
		return "empty";
	}

	public String onRequestKoth(OfflinePlayer player, Koth koth, String placeHolder) {
		Optional<?> optional = getOptionalValue(player, koth, placeHolder);

		if (optional != null) {
			return parseOptional(optional, placeHolder);
		}

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

		if (placeHolder.equalsIgnoreCase("score") && player != null)
			return String.valueOf(koth.getGame().getScoreTracker().getPoints(player.getUniqueId()));

		if (placeHolder.equalsIgnoreCase("countdown"))
			return TimeUtil.formatTime(Duration.between(LocalTime.now(), koth.getCountdown().getNextStartTime()).getSeconds());

		if (placeHolder.equalsIgnoreCase("timeLeft")) {
			Game game = koth.getGame();
			if (game != null) {
				return TimeUtil.formatTime(koth.getGame().getGameLoop().getTimeLeft());
			}
			return null;
		}

		if (placeHolder.equalsIgnoreCase("currentCapturer"))
			return koth.getGame().getGameLoop().getCapper() == null ? "No one is capping" : koth.getGame().getGameLoop().getCapper().getName();

		return null;
	}

	private Optional<?> getOptionalValue(OfflinePlayer player, Koth koth, String placeHolder) {
		Pattern scorePlayerPattern = Pattern.compile("scorePlayer(\\d+)");
		Matcher scorePlayerMatcher = scorePlayerPattern.matcher(placeHolder);
		if (scorePlayerMatcher.matches()) {
			int position = Integer.parseInt(scorePlayerMatcher.group(1));
			return koth.getGame().getScoreTracker().getPlayerForPosition(position);
		}

		Pattern scorePointsPattern = Pattern.compile("scorePoints(\\d+)");
		Matcher scorePointsMatcher = scorePointsPattern.matcher(placeHolder);
		if (scorePointsMatcher.matches()) {
			int position = Integer.parseInt(scorePointsMatcher.group(1));
			return koth.getGame().getScoreTracker().getPointsForPosition(position);
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