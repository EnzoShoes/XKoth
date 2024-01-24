package me.enzosocks.xkoth_socks.instance.koth;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class KothSchedule {
	private long nextStartTime;
	private List<LocalTime> startTimes;

	public KothSchedule() {
		this(new ArrayList<>());
	}

	public KothSchedule(List<LocalTime> startTimes) {
		this.startTimes = startTimes;
	}

	public List<LocalTime> getStartTimes() {
		return startTimes;
	}

	public long getNextStartTime() {
		long currentTime = System.currentTimeMillis() / 1000;
		for (LocalTime startTime : startTimes) {
			long startTimeSeconds = startTime.getHour() * 3600 + startTime.getMinute() * 60 + startTime.getSecond();
			if (startTimeSeconds > currentTime)
				return startTimeSeconds;
		}
		return -1;
	}
}
