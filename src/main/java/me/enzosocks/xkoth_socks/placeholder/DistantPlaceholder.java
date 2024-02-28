package me.enzosocks.xkoth_socks.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class DistantPlaceholder extends PlaceholderExpansion {

	private final LocalPlaceholder localPlaceholder;

	public DistantPlaceholder(LocalPlaceholder localPlaceholder) {
		this.localPlaceholder = localPlaceholder;
	}

	@Override
	public @NotNull String getIdentifier() {
		return "xkoth";
	}

	@Override
	public @NotNull String getAuthor() {
		return localPlaceholder.getPlugin().getDescription().getAuthors().get(0);
	}

	@Override
	public @NotNull String getVersion() {
		return localPlaceholder.getPlugin().getDescription().getVersion();
	}

	@Override
	public boolean persist() {
		return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
	}

	@Override
	public String onRequest(OfflinePlayer player, String params) {
		return localPlaceholder.onRequest(player, params);
	}
}
