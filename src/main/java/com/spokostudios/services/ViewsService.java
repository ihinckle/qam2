package com.spokostudios.services;

import com.spokostudios.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewsService {
	private static ViewsService instance;

	public static ViewsService getInstance(){
		if(instance == null){
			instance = new ViewsService();
		}

		return instance;
	}

	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}
}
