package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Searcher;

import java.io.File;
import java.io.FileNotFoundException;


public class AllFileController {

    private static final Logger log = LogManager.getLogger(AllFileController.class);

    @FXML
    JFXButton addFileButton;

    //just for prototyping
    public String[] addFileButtonOnAction() throws Exception {
        //dunno, it should be somehow connected with our model
        //maybe here, maybe in outer util class ¯\_(ツ)_/¯

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainWindow.fxml"));
//
//        Pane pane = loader.load();
//
//        MainWindowController controller = loader.getController();
//        Indexer indexer = controller.getIndexer();
//        Searcher searcher = controller.getSearcher();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            System.out.println(String.format("File path: %s, file name: %s", path, fileName));
            return new String[]{path, fileName};
        } else {
            throw new FileNotFoundException("Couldn't find file");
        }

    }
}

