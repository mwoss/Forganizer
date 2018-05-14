package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    private final Logger log = Logger.getLogger(MainWindowController.class);
    private final Map<String, Node> controllerMap = new HashMap<>();

    @FXML
    private BorderPane mainView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("MainWindow Controller initialized");
    }

    @FXML
    private void handleChangeView(ActionEvent event) {
        try {
            String menuItemID = ((JFXButton) event.getSource()).getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/" + menuItemID + ".fxml"));
            if (controllerMap.get(menuItemID) == null){
                Node view = loader.load();
                controllerMap.put(menuItemID, view);
                mainView.setCenter(view);
            } else{
                mainView.setCenter(controllerMap.get(menuItemID));
            }
            log.info("Loaded " + menuItemID + " view");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
