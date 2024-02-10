package com.example.familytree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Original root pane
        BorderPane root = new BorderPane();

        // Load your main content into the root pane
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        root.setCenter(fxmlLoader.load()); // Assuming you want your FXML content in the center of the BorderPane

        // Wrap the root pane in a ScrollPane for both vertical and horizontal scrolling
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true); // This will fit the content width to the width of the scroll pane to avoid unnecessary horizontal scroll
        scrollPane.setFitToHeight(true); // This will fit the content height to the height of the scroll pane to avoid unnecessary vertical scroll

        // Now set the scrollPane as the scene's root
        Scene scene = new Scene(scrollPane, 800, 600);

        stage.setTitle("Family Tree Application");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
