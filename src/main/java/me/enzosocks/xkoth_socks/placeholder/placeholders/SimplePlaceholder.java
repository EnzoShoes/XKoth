package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SimplePlaceholder implements XPlaceholder {
	private final String placeholder;
	private final Function<Koth, String> placeholderSupplier;
	private final String defaultValue;

	public SimplePlaceholder(String placeholder, Function<Koth, String> placeholderSupplier) {
		this(placeholder, placeholderSupplier, "none");
	}

	public SimplePlaceholder(String placeholder, Function<Koth, String> placeholderSupplier, String defaultValue) {
		this.placeholder = placeholder;
		this.placeholderSupplier = placeholderSupplier;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean matches(String placeHolder) {
		return placeHolder.equalsIgnoreCase(placeholder);
	}

	@Override
	public String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player) {
		try {
			return placeholderSupplier.apply(koth);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
