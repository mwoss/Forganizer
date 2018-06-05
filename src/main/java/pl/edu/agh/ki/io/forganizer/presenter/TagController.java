package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import pl.edu.agh.ki.io.forganizer.model.File;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Function;



public class TagController implements Initializable {
    private static final Logger log = Logger.getLogger(TagController.class);
    private FileManager fileManager = new FileManager(Const.pathIndex, Language.ENGLISH);
    private ObservableList<File> tagList;
    private ObservableList<File> filesList;

    @FXML
    private JFXTreeTableView<File> tagFileTable;

    @FXML
    private JFXTreeTableColumn<File, String> tagFileNameColumn;

    @FXML
    private JFXTreeTableColumn<File, String> tagFilePathColumn;

    @FXML
    private JFXTreeTableView<File> tagTable;

    @FXML
    JFXTreeTableColumn<File, String> tagColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTagTableView();
        log.info("Tag Controller initialized");
    }

    private void setupTagTableView() {
        setupCellValueFactory(tagColumn, File::getTagProperty);
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            tagList = fileManager.getFilesWithNonEmptyTag(dir);
        } catch (IOException e) {
            tagList = FXCollections.observableArrayList();
            log.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            tagTable.setRoot(new RecursiveTreeItem<>(
                    tagList,
                    RecursiveTreeObject::getChildren
            ));
        }
        tagTable.setShowRoot(false);
        addSelectedItemListener();
    }

    private void addSelectedItemListener() {
        tagTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        setupCellValueFactory(tagFileNameColumn, File::getNameProperty);
                        setupCellValueFactory(tagFilePathColumn, File::getPathProperty);
                        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
                            File selectedFile = newSelection.getValue();
                            filesList = fileManager.getFilesByTag(selectedFile.getTag(), dir);
                        } catch (IOException e) {
                            filesList = FXCollections.observableArrayList();
                            log.error(e.getMessage());
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        } finally {
                            tagFileTable.setRoot(new RecursiveTreeItem<>(
                                    filesList,
                                    RecursiveTreeObject::getChildren
                            ));
                        }
                    }
                });
        tagFileTable.setShowRoot(false);
    }


    private <T> void setupCellValueFactory(JFXTreeTableColumn<File, T> column, Function<File, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<File, T> param) -> {
            if (column.validateValue(param) && param.getValue().getValue() != null) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }


}
