module com.example.a_la_carte {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.a_la_carte to javafx.fxml;
    exports com.example.a_la_carte;
}