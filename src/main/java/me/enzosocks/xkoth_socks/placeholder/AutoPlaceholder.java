package me.enzosocks.xkoth_socks.placeholder;

import org.bukkit.entity.Player;

import java.util.function.BiFunction;

public class AutoPlaceholder {

	private final String startWith;
	private final BiFunction<Player, String, String> biFunction;

	/**
	 * @param startWith
	 * @param biFunction
	 */
	public AutoPlaceholder(String startWith, BiFunction<Player, String, String> biFunction) {
		super();
		this.startWith = startWith;
		this.biFunction = biFunction;
	}

	/**
	 * @return the startWith
	 */
	public String getStartWith() {
		return startWith;
	}

	/**
	 * @return the biFunction
	 */
	public BiFunction<Player, String, String> getBiFunction() {
		return biFunction;
	}

	public String accept(Player player, String value) {
		return this.biFunction.apply(player, value);
	}

}
