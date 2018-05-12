package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    private final Logger log = Logger.getLogger(MainWindowController.class);
    private FileManager fileManager;

    @FXML
    private AllFilesController allFilesController;

    @FXML
    private BorderPane mainView;

    // This looks shitty, but net says it should be done like this ;/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("MainWindow Controller initialized");
        this.fileManager = new FileManager(Const.pathIndex, Language.ENGLISH);
        allFilesController.setFileManager(fileManager);
    }

    @FXML
    private void handleChangeView(ActionEvent event) {
        try {
            String menuItemID = ((JFXButton) event.getSource()).getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/" + menuItemID + ".fxml"));
            mainView.setCenter(loader.load());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
