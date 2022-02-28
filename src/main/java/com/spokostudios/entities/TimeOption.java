package com.spokostudios.entities;

import com.spokostudios.services.LocalizationService;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeOption {
	private int time;
	private LocalTime timeObject;
	private OffsetTime timeInUTC;

	public TimeOption(int time){
		this.time = time;

		LocalizationService ls = LocalizationService.getInstance();

		OffsetTime offsetTime = OffsetTime.of(time, 0, 0, 0, ls.getHomeOfficeOffset());
		timeObject = offsetTime.withOffsetSameInstant(ls.getZoneOffset()).toLocalTime();

		timeInUTC = offsetTime.withOffsetSameInstant(ZoneOffset.UTC);
	}

	public int getTimeInt(){
		return time;
	}

	public OffsetTime getTimeInUTC() {
		return timeInUTC;
	}

	public String toString(){
		return timeObject.format(DateTimeFormatter.ofPattern("h:mm a"));
	}
}
