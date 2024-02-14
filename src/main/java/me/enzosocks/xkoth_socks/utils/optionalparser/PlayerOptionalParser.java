package me.enzosocks.xkoth_socks.utils.optionalparser;

import org.bukkit.OfflinePlayer;

import java.util.Optional;

public class PlayerOptionalParser implements OptionalParser<OfflinePlayer> {
	@Override
	public String parse(Optional<OfflinePlayer> player) {
		return player.map(OfflinePlayer::getName).orElse("No one");
	}
}