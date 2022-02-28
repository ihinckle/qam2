package com.spokostudios.entities;

import com.spokostudios.services.LocalizationService;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * A time object to represent a choice of time for a start or end time
 */
public class TimeOption {
	private int time;
	private LocalTime timeObject;
	private OffsetTime timeInUTC;

	public TimeOption(int time){
		this.time = time;

		LocalizationService ls = null;
		try {
			ls = LocalizationService.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		OffsetTime offsetTime = OffsetTime.of(time, 0, 0, 0, ls.getHomeOfficeOffset());
		timeObject = offsetTime.withOffsetSameInstant(ls.getZoneOffset()).toLocalTime();

		timeInUTC = offsetTime.withOffsetSameInstant(ZoneOffset.UTC);
	}

	/**
	 * @return The integer used to generate the TimeOption
	 */
	public int getTimeInt(){
		return time;
	}

	/**
	 * @return The TimeOption in UTC
	 */
	public OffsetTime getTimeInUTC() {
		return timeInUTC;
	}

	/**
	 * @return The TimeOption in local time
	 */
	public String toString(){
		return timeObject.format(DateTimeFormatter.ofPattern("h:mm a"));
	}
}
