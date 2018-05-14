package pl.edu.agh.ki.io.forganizer.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;
import pl.edu.agh.ki.io.forganizer.view.MainWindowView;

import java.io.IOException;
import java.nio.file.Paths;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindowView mainWindowView = new MainWindowView(primaryStage);
        mainWindowView.showStage();
    }
}
