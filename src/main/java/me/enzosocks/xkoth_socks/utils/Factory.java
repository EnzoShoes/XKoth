package me.enzosocks.xkoth_socks.utils;

public interface Factory<U, T> {
	T create(U u);
}
