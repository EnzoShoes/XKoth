package me.enzosocks.xkoth_socks.utils;

import me.enzosocks.xkoth_socks.XPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class Persist {

	private final XPlugin plugin;

	public Persist(XPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean save(Object instance, String name) {
		return save(instance, new File(plugin.getDataFolder(), name + ".json"));
	}

	public boolean save(Object instance, File file) {
		try {
			write(file, plugin.getGson().toJson(instance));
		} catch (Exception e) {
			Logger.error("cannot save file " + file.getAbsolutePath());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public <T> T loadOrSaveDefault(T def, Class<T> clazz) {
		return loadOrSaveDefault(def, clazz, getFile(clazz));
	}

	public <T> T loadOrSaveDefault(T def, Class<T> clazz, String name) {
		return loadOrSaveDefault(def, clazz, getFile(name));
	}

	public <T> T loadOrSaveDefault(T def, Class<T> clazz, File file) {
		if (!file.exists()) {
			Logger.info("Creating default: " + file);
			this.save(def, file);
			return def;
		}

		T loaded = this.load(clazz, file);

		if (loaded == null) {
			Logger.warning("Using default as I failed to load: " + file);

			// Create new config backup

			File backup = new File(file.getPath() + "_bad");
			if (backup.exists())
				backup.delete();
			Logger.warning("Backing up copy of bad file to: " + backup);

			file.renameTo(backup);

			return def;
		} else {
			Logger.info(file.getPath() + " loaded successfully !");
		}

		return loaded;
	}

	public <T> T load(Class<T> clazz, File file) {
		String content = DiscUtils.readCatch(file);
		if (content == null) {
			return null;
		}

		try {
			T instance = plugin.getGson().fromJson(content, clazz);
			return instance;
		} catch (Exception ex) {
			Logger.error(ex.getMessage());
		}

		return null;
	}


	public static void write(File file, String content) throws IOException {
		writeBytes(file, utf8(content));
	}

	public static byte[] utf8(String string) {
		return string.getBytes(StandardCharsets.UTF_8);
	}

	private static void writeBytes(File file, byte[] bytes) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		out.write(bytes);
		out.close();
	}

	public File getFile(String name) {
		return new File(plugin.getDataFolder(), name + ".json");
	}

	public File getFile(Class<?> clazz) {
		return getFile(getName(clazz));
	}

	public File getFile(Object obj) {
		return getFile(getName(obj));
	}

	public File getFile(Type type) {
		return getFile(getName(type));
	}

	public static String getName(Class<?> clazz) {
		return clazz.getSimpleName().toLowerCase();
	}

	public static String getName(Object o) {
		return getName(o.getClass());
	}

	public static String getName(Type type) {
		return getName(type.getClass());
	}
}
