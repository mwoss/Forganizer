package pl.edu.agh.ki.io.forganizer.presenter;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.model.File;
import pl.edu.agh.ki.io.forganizer.model.FileManager;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.utils.Const;
import javafx.beans.value.ChangeListener;
import pl.edu.agh.ki.io.forganizer.utils.FileLoader;
import pl.edu.agh.ki.io.forganizer.utils.SizeConverter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Function;

//TODO: Add this controller to controller map at app start
//TODO: should be optimized somehow
public class AllFilesController implements Initializable {

    private static final Logger log = Logger.getLogger(AllFilesController.class);
    private FileLoader fileChooser;
    private FileManager fileManager = new FileManager(Const.pathIndex, Language.ENGLISH);
    private ObservableList<File> filesList;

    private TextInputDialog inputDialog;
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
        this.fileChooser = new FileLoader();
        this.inputDialog = new TextInputDialog("");
        this.textArea = new TextArea();
        setupTableView();
        setupInputDialog();
        log.info("AllFile Controller initialized");
    }

    private void setupInputDialog() {
        inputDialog.setHeaderText("Input data in box below");
        inputDialog.setGraphic(null);
        inputDialog.setResizable(true);
        inputDialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/view/stylesheet/dialogStylesheet.css").toExternalForm()
        );
        this.textArea.setWrapText(true);
        inputDialog.getDialogPane().setContent(textArea);
    }


    private void setupTableView() {
        setupCellValueFactory(fileNameColumn, File::getNameProperty);
        setupCellValueFactory(filePathColumn, File::getPathProperty);
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            filesList = fileManager.getAllFiles(dir);
        } catch (IOException e) {
            filesList = FXCollections.observableArrayList();
            log.error(e.getMessage());
        } finally {
            allFileTableView.setRoot(new RecursiveTreeItem<>(
                    filesList,
                    RecursiveTreeObject::getChildren
            ));
        }
        allFileTableView.setShowRoot(false);
        addSelectedItemListener();
        contexMenuListener();
        searchField.textProperty().addListener(setupSearchField(allFileTableView));
    }

    private void contexMenuListener(){
//        allFileTableView.setRowFactory(
//                new Callback<TreeTableView<File>, TreeTableRow<File>>() {
//                    @Override
//                    public TreeTableRow<File> call(TreeTableView<File> param) {
//                        final TreeTableRow<File> row = new TreeTableRow<>();
//                        final ContextMenu rowMenu = new ContextMenu();
//                        MenuItem editItem = new MenuItem("Edit");
//                        MenuItem removeItem = new MenuItem("Remove");
//                        removeItem.setOnAction(new EventHandler<ActionEvent>() {
//                            @Override
//                            public void handle(ActionEvent event) {
//                                System.out.println("remove");
//                            }
//                        });
//                        rowMenu.getItems().addAll(editItem, removeItem);
//                        return row;
//                    }
//                }
//        );
        MenuItem menuItem = new MenuItem("Remove");
        menuItem.setOnAction((ActionEvent event) -> {
            System.out.println("remove item");
            Object item = allFileTableView.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + item);
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(menuItem);
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

    //TODO: handle unrecognized files type
    public void addFileButtonOnAction() {
        java.io.File selectedFile = fileChooser.choseFile();
        try {
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                String fileName = selectedFile.getName();
                File newFile = new File(fileName, path, selectedFile.length(),
                        Files.probeContentType(selectedFile.toPath()));
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

    public void addCommentButtonOnAction() {
        textArea.clear();
        inputDialog.setTitle("Comment section");
        inputDialog.showAndWait();
        String result = textArea.getText();
        File file = allFileTableView.getSelectionModel()
                .selectedItemProperty()
                .get()
                .getValue();
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            fileManager.updateFile(file.withComment(result), dir);
        } catch (IOException e) {
            log.error(e);
        }
        commentLabel.setText(result);
    }

    public void addTagButtonOnAction() {
        textArea.clear(); // oh is total workaround, but i don't know any better approach for this
        inputDialog.setTitle("Tag section");
        inputDialog.showAndWait();
        String result = textArea.getText();
        File file = allFileTableView.getSelectionModel()
                .selectedItemProperty()
                .get()
                .getValue();
        try (Directory dir = FSDirectory.open(Paths.get(Const.pathIndex))) {
            fileManager.updateFile(file.withTag(result), dir);
        } catch (IOException e) {
            log.error(e);
        }
        tagLabel.setText(result);
    }
}


