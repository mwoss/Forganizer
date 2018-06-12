package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.model.File;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;
import javafx.beans.value.ChangeListener;
import pl.edu.agh.ki.io.forganizer.utils.FileLoader;
import pl.edu.agh.ki.io.forganizer.utils.PathConverter;
import pl.edu.agh.ki.io.forganizer.utils.SizeConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Function;

public class AllFilesController implements Initializable {

    private static final Logger log = Logger.getLogger(AllFilesController.class);
    private FileLoader fileChooser = new FileLoader();
    private FileManager fileManager = new FileManager(Const.pathIndex, Language.ENGLISH);
    private ObservableList<File> filesList;

    private TextInputDialog commentDialog;
    private TextInputDialog tagDialog;
    private Alert confirmationDialog;
    private Alert warningDialog;
    private TextArea textArea;

    @FXML
    private JFXTreeTableView<File> allFileTableView;
    @FXML
    private JFXTreeTableColumn<File, String> fileNameColumn;
    @FXML
    private JFXTreeTableColumn<File, String> filePathColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label tagLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label commentLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.commentDialog = new TextInputDialog("");
        this.tagDialog = new TextInputDialog("");
        this.confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        this.warningDialog = new Alert(Alert.AlertType.WARNING);
        this.textArea = new TextArea();
        this.textArea.setWrapText(true);
        commentDialog.getDialogPane().setContent(textArea);
        setupTableView();
        setupInputDialog(commentDialog, "Input comment in box below", "Comment section");
        setupInputDialog(tagDialog, "Input tag in box below", "Tag section");
        setupWarningDialog();
        setupConfirmationDialog();
        log.info("AllFile Controller initialized");
    }

    private void setupInputDialog(TextInputDialog inputDialog, String headerText, String titleText) {
        inputDialog.setHeaderText(headerText);
        inputDialog.setTitle(titleText);
        inputDialog.setGraphic(null);
        inputDialog.setResizable(true);
        inputDialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/view/stylesheet/dialogStylesheet.css").toExternalForm()
        );
    }

    private void setupConfirmationDialog() {
        confirmationDialog.setHeaderText("Are you sure want to delete this file?");
    }

    private void setupWarningDialog(){
        warningDialog.setTitle("Warning");
        warningDialog.setHeaderText(null);
        warningDialog.setContentText("File or folder doesn't exist");
    }

    private void setupTableView() {
        setupCellValueFactory(fileNameColumn, File::getNameProperty);
        setupCellValueFactory(filePathColumn, File::getPathProperty);
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            filesList = fileManager.getAllFiles(dir);
        } catch (IOException | IllegalArgumentException e) {
            filesList = FXCollections.observableArrayList(param ->
                    new Observable[]{param.getTagProperty()});
            log.error(e.getMessage());
        } finally {
            allFileTableView.setRoot(new RecursiveTreeItem<>(
                    filesList,
                    RecursiveTreeObject::getChildren
            ));
        }
        allFileTableView.setShowRoot(false);
        addSelectedItemListener();
        contextMenuListener();
        searchField.textProperty().addListener(setupSearchField(allFileTableView));
    }

    private void contextMenuListener() {
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(newAddCommentContextItem(), newAddTagContextItem(),
                removeContextItem(), showInExplorerContextItem());
        allFileTableView.setContextMenu(menu);
    }


    private void addSelectedItemListener() {
        allFileTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        File selectedFile = newSelection.getValue();
                        sizeLabel.setText(SizeConverter.getReadableFileSize(selectedFile.getSize()));
                        typeLabel.setText(selectedFile.getFileType());
                        tagLabel.setText(selectedFile.getTag());
                        commentLabel.setText(selectedFile.getComment());
                    }
                });
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
                            || file.getPath().contains(newVal)
                            || file.getComment().contains(newVal);
                });
    }

    public void addFileButtonOnAction() {
        java.io.File selectedFile = fileChooser.choseFile();
        try {
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                String fileName = selectedFile.getName();
                String fileType = Files.probeContentType(selectedFile.toPath());
                File newFile = new File(fileName, path, selectedFile.length(),
                        fileType != null ? fileType : "undefined");
                fileManager.addFile(
                        newFile,
                        FSDirectory.open(Paths.get(Const.pathIndex))
                );
                filesList.add(newFile);
                log.info(String.format("File path: %s, file name: %s", path, fileName));
            } else {
                log.error("File not specified");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private MenuItem newAddCommentContextItem() {
        MenuItem addCommentItem = new MenuItem("Add comment");
        addCommentItem.setOnAction((ActionEvent event) -> {
            textArea.clear(); // workaround, but i don't know any better approach for this
            commentDialog.showAndWait();
            String result = textArea.getText();
            File file = getSelectedFile();
            try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
                fileManager.updateFile(file.withComment(result), dir);
            } catch (IOException e) {
                log.error(e);
            }
            commentLabel.setText(result);
        });
        return addCommentItem;
    }

    private MenuItem newAddTagContextItem() {
        MenuItem addTagItem = new MenuItem("Add tag");
        addTagItem.setOnAction((ActionEvent event) -> {
            tagDialog.getEditor().clear();
            tagDialog.getEditor().textProperty().addListener((o, oldVal, newVal) ->{
                if(tagDialog.getEditor().getText().contains(" ")){
                    tagDialog.getEditor().setText(tagDialog.getEditor().getText().split(" ")[0]);
                }
            });
            tagDialog.showAndWait().ifPresent(result -> {
                File file = getSelectedFile();
                file.setTag(result); // Ultra workaround, not efficient but works
                try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
                    fileManager.updateFile(file, dir);
                } catch (IOException e) {
                    log.error(e);
                }
                tagLabel.setText(result);
            });
        });
        return addTagItem;
    }

    private MenuItem removeContextItem() {
        MenuItem removeItem = new MenuItem("Remove");
        removeItem.setOnAction((ActionEvent event) -> {
            confirmationDialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    removeFileFromApp();
                }
            });
        });
        return removeItem;
    }

    private MenuItem showInExplorerContextItem() {
        MenuItem addCommentItem = new MenuItem("Show in explorer");
        addCommentItem.setOnAction((ActionEvent event) -> {
            File file = getSelectedFile();
            try {
                Desktop.getDesktop().open(new java.io.File(PathConverter.getPathForExplorer(file.getPath())));
            } catch (IOException | IllegalArgumentException e) {
                warningDialog.showAndWait().ifPresent(result -> {
                    if (result == ButtonType.OK){
                        removeFileFromApp();
                    }
                });
            }
        });
        return addCommentItem;
    }

    private File getSelectedFile() {
        return allFileTableView.getSelectionModel()
                .selectedItemProperty()
                .get()
                .getValue();
    }

    private void removeFileFromApp(){
        File file = getSelectedFile();
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            fileManager.removeFile(file, dir);
            filesList.removeAll(file);
            log.info(String.format("File %s removed from app", file.getName()));
        } catch (IOException e) {
            log.error(e);
        }
    }

    public ObservableList<File> getFileList() {
        return filesList;
    }
}


