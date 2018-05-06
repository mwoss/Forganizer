package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Searcher;

import java.io.IOException;

public class MainWindowController {

    private Indexer indexer = new Indexer("index", "english");
    private Searcher searcher = new Searcher("index", "english");

    public MainWindowController() {
        System.out.println("started controller");
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
