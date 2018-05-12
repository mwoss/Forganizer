package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.search.Searcher;

import java.io.IOException;

public class MainWindowController {

    private static final Logger log = Logger.getLogger(AllFileController.class);
    private Indexer indexer = new Indexer("index", Language.ENGLISH);
    private Searcher searcher = new Searcher("index", Language.ENGLISH);

    public MainWindowController() {
        log.info("MainWindow Controller initialized");
    }

    public Indexer getIndexer() {
        return indexer;
    }

    public Searcher getSearcher() {
        return searcher;
    }

    @FXML
    private BorderPane mainView;

    @FXML
    private void handleChangeView(ActionEvent event) {
        try {
            String menuItemID = ((JFXButton) event.getSource()).getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/" + menuItemID + ".fxml"));
            mainView.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
