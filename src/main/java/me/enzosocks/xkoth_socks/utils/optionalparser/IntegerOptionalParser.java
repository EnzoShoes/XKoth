package me.enzosocks.xkoth_socks.utils.optionalparser;

import java.util.Optional;

public class IntegerOptionalParser implements OptionalParser<Integer> {
	@Override
	public String parse(Optional<Integer> optional) {
		return optional.map(String::valueOf).orElse("0");
	}
}