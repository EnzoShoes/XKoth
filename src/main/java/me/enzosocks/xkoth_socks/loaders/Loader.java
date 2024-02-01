package me.enzosocks.xkoth_socks.loaders;

import org.bukkit.configuration.file.FileConfiguration;

public interface Loader<T> {

	T load(FileConfiguration config, String path, Object... args);
}
