package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.model.File;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import javafx.beans.value.ChangeListener;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Function;


public class AllFilesController implements Initializable {

    private static final Logger log = Logger.getLogger(AllFilesController.class);
    private FileChooser fileChooser;
    private ObjectProperty<FileManager> fileManager;

    @FXML
    private JFXButton addFileButton;
    @FXML
    private JFXTreeTableView<File> allFileTableView;
    @FXML
    private JFXTreeTableColumn<File, String> fileNameColumn;
    @FXML
    private JFXTreeTableColumn<File, String> filePathColumn;
    @FXML
    private JFXTextField searchField;

    public AllFilesController(){
        this.fileManager = new SimpleObjectProperty<>();
    }

    // TODO: child controllers are initialized every time certain button are clicked, should be initialized once fix it
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fileChooser = setupFileChooser();
        log.info("AllFile Controller initialized");
    }

    public void setupTableView() {
        setupCellValueFactory(fileNameColumn, File::getNameProperty);
        setupCellValueFactory(filePathColumn, File::getPathProperty);
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            allFileTableView.setRoot(new RecursiveTreeItem<>(
                    fileManagerProperty().get().getAllFiles(dir),
                    RecursiveTreeObject::getChildren));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        allFileTableView.setShowRoot(false);
        searchField.textProperty().addListener(setupSearchField(allFileTableView));
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<File, T> column, Function<File, ObservableValue<T>> mapper) {
        column.prefWidthProperty().bind(allFileTableView.widthProperty().divide(2));
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<File, T> param) -> {
            if (column.validateValue(param) && param.getValue().getValue() != null) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private ChangeListener<String> setupSearchField(JFXTreeTableView<File> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(fileProp -> {
                    final File file = fileProp.getValue();
                    return file.getName().contains(newVal)
                            || file.getPath().contains(newVal);
                });
    }

    public void addFileButtonOnAction() {
        java.io.File selectedFile = fileChooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                String fileName = selectedFile.getName();
                fileManagerProperty().get().addFile(
                        new File(fileName, path),
                        FSDirectory.open(Paths.get(Const.pathIndex))
                );
                log.info(String.format("File path: %s, file name: %s", path, fileName));
                System.out.println(fileManager.toString());
            } else {
                log.error("File not specified");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private FileChooser setupFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));
        return fileChooser;
    }

//    public void setFileManager(FileManager fileManager) {
//        this.fileManager = fileManager;
//    }

    public ObjectProperty<FileManager> fileManagerProperty() {
        return fileManager;
    }
}


