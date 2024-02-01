package me.enzosocks.xkoth_socks.instance.koth;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KothSchedule {
	private long nextStartTime;
	private List<LocalTime> startTimes;

	public KothSchedule() {
		this(new ArrayList<>());
	}

	public KothSchedule(List<LocalTime> startTimes) {
		this.startTimes = startTimes.stream().sorted().collect(Collectors.toList());
	}

	public List<LocalTime> getStartTimes() {
		return new ArrayList<>(startTimes);
	}

	public LocalTime getNextStartTime() {
		LocalTime currentTime = LocalTime.now();
		for (LocalTime startTime : startTimes) {
			if (startTime.isAfter(currentTime))
				return startTime;
		}
		// if no start time is after the current time, then next start time must be the first one "tomorrow"
		return startTimes.isEmpty() ? null : startTimes.get(0);
	}
}
