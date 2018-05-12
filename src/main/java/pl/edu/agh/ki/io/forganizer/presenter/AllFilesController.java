package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.utils.Consts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class AllFilesController implements Initializable{

    private static final Logger log = Logger.getLogger(AllFilesController.class);
    private FileChooser fileChooser;
    private Indexer indexer;

    @FXML
    JFXButton addFileButton;

    // Child controllers are initialized every time certain button are clicked,
    // fix it
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fileChooser = initializeFileChooser();
        log.info("AllFile Controller initialized");
    }

    //just for prototyping
    public void addFileButtonOnAction()  {
        //dunno, it should be somehow connected with our model
        //maybe here, maybe in outer util class ¯\_(ツ)_/¯
        File selectedFile = fileChooser.showOpenDialog(null);
        try{
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                String fileName = selectedFile.getName();
                indexer.addFile(
                        createNewDocument(fileName, path),
                        FSDirectory.open(Paths.get(Consts.pathIndex))
                );
                log.info(String.format("File path: %s, file name: %s", path, fileName));
            } else {
                log.error("File not specified");
            }

        }catch (IOException e){
            log.error(e.getMessage());
        }
    }
    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }
    private Document createNewDocument(String fileName, String path) {
        Document document = new Document();
        document.add(new StringField("name", fileName, Field.Store.YES));
        document.add(new StringField("path", path, Field.Store.YES));
        return document;
    }

    private FileChooser initializeFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser;
    }
}

