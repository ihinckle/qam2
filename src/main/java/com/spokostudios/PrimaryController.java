package com.spokostudios;

import java.io.IOException;
import javafx.fxml.FXML;
import java.util.Locale;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
