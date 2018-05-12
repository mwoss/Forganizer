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
import pl.edu.agh.ki.io.forganizer.utils.Consts;

import java.io.IOException;

public class MainWindowController {

    private final Logger log = Logger.getLogger(AllFilesController.class);
    private Indexer indexer = new Indexer(Consts.pathIndex, Language.ENGLISH);
    private Searcher searcher = new Searcher(Consts.pathIndex, Language.ENGLISH);


    @FXML
    private BorderPane mainView;

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
    private void handleChangeView(ActionEvent event) {
        try {
            String menuItemID = ((JFXButton) event.getSource()).getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/" + menuItemID + ".fxml"));
//            loader.setRoot(this);
            mainView.setCenter(loader.load());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
