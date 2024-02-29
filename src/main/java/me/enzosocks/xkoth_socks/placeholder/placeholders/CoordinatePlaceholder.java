package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class CoordinatePlaceholder implements XPlaceholder {
	private static final Pattern pattern = Pattern.compile("^[xyz]$");
	private final String defaultValue;

	public CoordinatePlaceholder(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean matches(String placeholder) {
		return pattern.matcher(placeholder).matches();
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		if (koth.getCuboid() == null) {
			return defaultValue;
		}

		switch (placeholder) {
			case "x":
				return String.valueOf((int) koth.getCuboid().getCenter().getX());
			case "y":
				return String.valueOf((int) koth.getCuboid().getCenter().getY());
			case "z":
				return String.valueOf((int) koth.getCuboid().getCenter().getZ());
			default:
				throw new IllegalArgumentException("Invalid placeholder error");
		}
	}

}
