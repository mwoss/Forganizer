package pl.edu.agh.ki.io.forganizer.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowView {
    private final String TITLE = "Forganizer";
    private final String FXML_FILE_NAME = "/view/fxml/mainWindow.fxml";
    private final String ICON_PATH = "/view/static/logo2.png";

    private Stage primaryStage;

    public MainWindowView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showStage() throws IOException {
        primaryStage.show();
        setupStage();
    }

    private void setupStage() throws IOException {
        primaryStage.setScene(new Scene(getRoot()));
        primaryStage.getIcons().add(new Image(MainWindowView.class.getResourceAsStream(ICON_PATH)));
        primaryStage.setTitle(TITLE);
        primaryStage.setWidth(900);
        primaryStage.setHeight(640);
    }

    private Parent getRoot() throws IOException {
        return FXMLLoader.load(getClass().getResource(FXML_FILE_NAME));
    }
}
