package pl.edu.agh.ki.io.forganizer.utils;

import javafx.stage.FileChooser;

import java.io.File;

public class FileLoader {
    private FileChooser fileChooser;

    public FileLoader() {
        this.fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));
    }

    public File choseFile(){
        return fileChooser.showOpenDialog(null);
    }

}
