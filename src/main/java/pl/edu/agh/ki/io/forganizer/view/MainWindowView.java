package pl.edu.agh.ki.io.forganizer.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowView {
    private final String TITLE = "Forganizer";
    private final String FXML_FILE_NAME = "/view/fxml/mainWindow.fxml";

    private Stage primaryStage;

    public MainWindowView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showStage() throws IOException {
        primaryStage.show();
        setupStage();
    }

    private void setupStage() throws IOException {
//        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(getRoot()));
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
    }

    private Parent getRoot() throws IOException {
        return FXMLLoader.load(getClass().getResource(FXML_FILE_NAME));
    }
}
