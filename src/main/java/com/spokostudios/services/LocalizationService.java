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

/**
 * A class for translations of language and time
 */
public class LocalizationService {
	private static LocalizationService localizationInstance = null;

	private static final ZoneId homeOfficeZone = ZoneId.of("US/Eastern");

	private static Locale locale = null;
	private static HashMap<String, String> textsMap = new HashMap<>();

	private static ZoneId zoneId;

	private LocalizationService() throws Exception {
		locale = Locale.getDefault();
		zoneId = ZoneId.systemDefault();

		String localizationPath = String.format("/com/spokostudios/localizations/%s.properties", locale.getLanguage());
		InputStream is = this.getClass().getResourceAsStream(localizationPath);

		if(is == null){
			localizationPath = String.format("/com/spokostudios/localizations/%s.properties", locale.getLanguage());
			is = this.getClass().getResourceAsStream(localizationPath);
		}

		if(is == null){
			throw new Exception("Please have at least an english localization file.");
		}

		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		/**
		 * Honestly because who wouldn't use a lambda with a forEach (unless it got completely complicated)
		 */
		br.lines().forEach(line -> {
			String[] keyValuePair = line.split("=");

			textsMap.put(keyValuePair[0], keyValuePair[1]);
		});
	}

	/**
	 * Get the instance
	 * @return The instance of this service
	 * @throws Exception
	 */
	public static LocalizationService getInstance() throws Exception {
		if(localizationInstance == null){
			localizationInstance = new LocalizationService();
		}

		return localizationInstance;
	}

	/**
	 * Gets the locale of the machine
	 * @return The locale
	 */
	public static Locale getLocale(){
		return locale;
	}

	/**
	 * Gets the zone ID for time conversions
	 * @return The zone ID
	 */
	public static ZoneId getZoneId(){
		return zoneId;
	}

	/**
	 * Gets the numerical time offset of the zone ID
	 * @return The numberical time offset
	 */
	public static ZoneOffset getZoneOffset(){
		return zoneId.getRules().getOffset(Instant.now());
	}

	/**
	 * Gets the localized text for a particular lookup
	 * @param key The lookup key in the properties file
	 * @return The test to display
	 */
	public static String getText(String key){
		return textsMap.get(key);
	}

	/**
	 * Formats a UTC time into the local time
	 * @param dateTimeUTC The UTC date and time to format
	 * @return The text to display
	 */
	public String formattedDateFromUTC(ZonedDateTime dateTimeUTC){
		ZonedDateTime date = dateTimeUTC.withZoneSameInstant(getZoneId());
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG);
		String formattedDate = date.format(formatter);
		return formattedDate;
	}

	/**
	 * Returns the time offset for the home office
	 * @return The home office time offset
	 */
	public static ZoneOffset getHomeOfficeOffset(){
		return homeOfficeZone.getRules().getOffset(Instant.now());
	}
}
