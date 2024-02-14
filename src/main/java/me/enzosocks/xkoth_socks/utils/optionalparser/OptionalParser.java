package me.enzosocks.xkoth_socks.utils.optionalparser;

import java.util.Optional;

public interface OptionalParser<T> {
	String parse(Optional<T> optional);
}
