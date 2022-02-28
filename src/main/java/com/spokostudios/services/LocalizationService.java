package com.spokostudios.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Locale;

public class LocalizationService {
	private static LocalizationService localizationInstance = null;

	private static final ZoneId homeOfficeZone = ZoneId.of("US/Eastern");

	private static Locale locale = null;
	private static HashMap<String, String> textsMap = new HashMap<>();
	private boolean shouldUseDefaults = false;

	private static ZoneId zoneId;

	private LocalizationService(){
		locale = Locale.getDefault();
		zoneId = ZoneId.systemDefault();

		String localizationPath = String.format("/com/spokostudios/localizations/%s.properties", locale.getLanguage());
		InputStream is = this.getClass().getResourceAsStream(localizationPath);

		if(is == null){
			shouldUseDefaults = true;
			return;
		}

		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		br.lines().forEach(line -> {
			String[] keyValuePair = line.split("=");

			textsMap.put(keyValuePair[0], keyValuePair[1]);
		});
	}

	public static LocalizationService getInstance(){
		if(localizationInstance == null){
			localizationInstance = new LocalizationService();
		}

		return localizationInstance;
	}

	public static Locale getLocale(){
		return locale;
	}

	public static ZoneId getZoneId(){
		return zoneId;
	}

	public static ZoneOffset getZoneOffset(){
		return zoneId.getRules().getOffset(Instant.now());
	}

	public static String getText(String key){
		return textsMap.get(key);
	}

	public Boolean useDefaults(){
		return shouldUseDefaults;
	}

	public String formattedDateFromUTC(ZonedDateTime dateTimeUTC){
		ZonedDateTime date = dateTimeUTC.withZoneSameInstant(getZoneId());
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG);
		String formattedDate = date.format(formatter);
		return formattedDate;
	}

	public static ZoneOffset getHomeOfficeOffset(){
		return homeOfficeZone.getRules().getOffset(Instant.now());
	}
}
