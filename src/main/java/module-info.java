module com.example.familytree {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.familytree to javafx.fxml;
    exports com.example.familytree;
}