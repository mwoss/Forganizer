package pl.edu.agh.ki.io.forganizer.model;

import pl.edu.agh.ki.io.forganizer.search.FolderType;
import pl.edu.agh.ki.io.forganizer.search.IndexFiles;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;

import java.io.IOException;
import java.util.List;

public class FileManager {

    private Indexer indexer;
    private Converter converter;

    public FileManager(String path,Language language) {
        indexer = new Indexer(path, language);
        converter = new Converter();
    }

    public void addFile(File file, FolderType folderType) throws IOException {
        indexer.addFile(converter.convertFileToDoc(file), folderType);
    }

    public void updateFile(File file) {

    }

    public void removeFile(File file) {

    }

    List<File> getAllFiles() {

        return null;
    }


}
