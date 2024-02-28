package me.enzosocks.xkoth_socks.schedulers.displays.bossbars;

import me.enzosocks.xkoth_socks.schedulers.displays.DisplayData;
import me.enzosocks.xkoth_socks.schedulers.displays.DisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class BossBarManager extends DisplayManager implements BossBarConfig {
	private final Map<UUID, IBossBar> playerBossBars = new HashMap<>();
	private final Function<BossBarConfig, IBossBar> bossBarSupplier;
	private final String title;
	private final BarStyle style;
	private final BarColor color;

	public BossBarManager(boolean enabled, boolean onlyShowWhenCapturing, int viewDistance, String title, BarStyle style, BarColor color, Function<BossBarConfig, IBossBar> bossBarSupplier) {
		super(enabled, viewDistance, onlyShowWhenCapturing);
		this.title = title;
		this.style = style;
		this.color = color;
		this.bossBarSupplier = bossBarSupplier;
	}

	public void doUpdate(DisplayData displayData) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!playerBossBars.containsKey(player.getUniqueId())) {
				playerBossBars.put(player.getUniqueId(), bossBarSupplier.apply(this));
			}
			IBossBar bossBar = playerBossBars.get(player.getUniqueId());
			bossBar.update(player, displayData);
		}
	}

	public void clear() {
		for (Map.Entry<UUID, IBossBar> entry : playerBossBars.entrySet()) {
			Player player = Bukkit.getPlayer(entry.getKey());
			if (entry.getValue() != null) {
				entry.getValue().clear(player);
			}
		}
		playerBossBars.clear();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public BarStyle getStyle() {
		return style;
	}

	@Override
	public BarColor getColor() {
		return color;
	}
}
