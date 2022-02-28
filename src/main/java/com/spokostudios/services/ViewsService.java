package com.spokostudios.services;

import com.spokostudios.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Honestly I thought that this service would come into more use, but sadly it did not.
 * Due to time constraints this remains as an artifact of a design that did not come to
 * fruition.
 */
public class ViewsService {
	private static ViewsService instance;

	/**
	 * @return The instance of this service
	 */
	public static ViewsService getInstance(){
		if(instance == null){
			instance = new ViewsService();
		}

		return instance;
	}

	/**
	 * Loads an FXML bundle from the desired string
	 * @param fxml The fxml template to load without the .fxml
	 * @return The bootstrapped FXML bundle
	 * @throws IOException
	 */
	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}
}
