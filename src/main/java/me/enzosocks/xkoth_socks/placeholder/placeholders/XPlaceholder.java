package me.enzosocks.xkoth_socks.placeholder.placeholders;

import me.enzosocks.xkoth_socks.instance.koth.Koth;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface XPlaceholder {
	boolean matches(String placeHolder);

	String parse(String placeholder, @NotNull Koth koth, OfflinePlayer player);
}