package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.RAMDirectory;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Searcher;
import pl.edu.agh.ki.io.forganizer.utils.Consts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;


public class AllFilesController {

    private static final Logger log = Logger.getLogger(AllFilesController.class);
    private MainWindowController mainController;

    @FXML
    JFXButton addFileButton;

    // Child controllers are initialized every time certain button are clicked,
    // fix it
    public AllFilesController() {
        log.info("AllFile Controller initialized");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainWindow.fxml"));
        this.mainController = loader.getController();
    }

    //just for prototyping
    public void addFileButtonOnAction() throws IOException {
        //dunno, it should be somehow connected with our model
        //maybe here, maybe in outer util class ¯\_(ツ)_/¯
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            mainController.getIndexer().addFile(
                    createNewDocument(fileName, path),
                    FSDirectory.open(Paths.get(Consts.pathIndex))
            );
            log.info(String.format("File path: %s, file name: %s", path, fileName));
        } else {
            log.error("Couldn't find file");
        }
    }

    private Document createNewDocument(String fileName, String path){
        Document document = new Document();
        document.add(new StringField("name", fileName, Field.Store.YES));
        document.add(new StringField("path", path, Field.Store.YES));
        return document;
    }
}

