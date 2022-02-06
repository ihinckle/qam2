module com.spokostudios {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

	opens com.spokostudios to javafx.fxml;
	opens com.spokostudios.viewcontrollers to javafx.fxml;

    exports com.spokostudios;
}
