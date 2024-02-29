package me.enzosocks.xkoth_socks.loaders;

import me.enzosocks.xkoth_socks.instance.koth.KothSchedule;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class KothScheduleLoader implements Loader<KothSchedule> {
	@Override
	public KothSchedule load(FileConfiguration config, String path, Object... args) {
		List<LocalTime> startTimes = new ArrayList<>();

		config.getStringList(path).forEach(time -> {
			String[] split = time.split(":");
			int hour = Integer.parseInt(split[0]);
			int minute = Integer.parseInt(split[1]);
			startTimes.add(LocalTime.of(hour, minute, 0));
		});
		
		return new KothSchedule(startTimes);
	}
}
