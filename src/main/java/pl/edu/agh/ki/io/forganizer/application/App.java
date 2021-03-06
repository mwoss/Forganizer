package pl.edu.agh.ki.io.forganizer.application;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.ki.io.forganizer.utils.SettingsManager;
import pl.edu.agh.ki.io.forganizer.view.MainWindowView;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SettingsManager settingsManager = new SettingsManager();
        settingsManager.setIndexFolder();
        MainWindowView mainWindowView = new MainWindowView(primaryStage);
        mainWindowView.showStage();
    }
}
