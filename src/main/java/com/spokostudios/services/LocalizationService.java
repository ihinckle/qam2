package com.spokostudios.services;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;

public class LocalizationService {
	private static LocalizationService localizationInstance = null;

	private static Locale locale = null;
	private static HashMap<String, String> textsMap = new HashMap<>();
	private boolean shouldUseDefaults = false;

	private LocalizationService(){
		locale = Locale.getDefault();

		String localizationPath = String.format("/com/spokostudios/localizations/%s.txt", locale.getLanguage());
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

	public static String getText(String key){
		return textsMap.get(key);
	}

	public Boolean useDefaults(){
		return shouldUseDefaults;
	}
}
