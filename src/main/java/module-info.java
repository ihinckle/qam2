module com.spokostudios {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

	opens com.spokostudios to javafx.fxml;
	opens com.spokostudios.views to javafx.fxml;
	opens com.spokostudios.entities to javafx.base;

    exports com.spokostudios;
}
