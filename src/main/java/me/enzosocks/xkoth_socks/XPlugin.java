package me.enzosocks.xkoth_socks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.enzosocks.xkoth_socks.utils.Persist;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Modifier;

public abstract class XPlugin extends JavaPlugin {
	private Gson gson;
	private Persist persist;

	protected void preEnable() {
		this.gson = getGsonBuilder().create();
		this.persist = new Persist(this);
	}

	protected GsonBuilder getGsonBuilder() {
		return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls()
				.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE);
	}

	public Gson getGson() {
		return gson;
	}

	public Persist getPersist() {
		return persist;
	}
}
