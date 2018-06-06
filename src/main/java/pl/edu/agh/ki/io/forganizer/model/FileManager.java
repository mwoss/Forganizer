package pl.edu.agh.ki.io.forganizer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.search.Searcher;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileManager {

    private String path;
    private Indexer indexer;
    private Searcher searcher;
    private Converter converter;

    public FileManager(String path, Language language) {
        this.path = path;
        this.indexer = new Indexer(path, language);
        this.searcher = new Searcher(path, language);
        this.converter = new Converter();
    }

    public void addFile(File file, Directory dir) throws IOException {
        indexer.addDoc(converter.convertFileToDoc(file), dir);
    }

    public void updateFile(File file, Directory dir) throws IOException {
        indexer.updateDoc(converter.convertFileToDoc(file), dir);
    }

    public void removeFile(File file, Directory dir) throws IOException {
        indexer.removeDoc(converter.convertFileToDoc(file), dir);
    }

    public ObservableList<File> getAllFiles(Directory dir) throws IOException {
        return Arrays.stream(searcher.getAllDocs(dir))
                .map(converter::convertDocToFile)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public ObservableList<File> getFilesByTag(String tag, Directory dir) throws IOException, ParseException {
        return Arrays.stream(searcher.searchField(Const.tagFileProperty, tag, dir, Searcher.MAX_DOC))
                .map(converter::convertDocToFile)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public ObservableList<File> getFilesWithNonEmptyTag(Directory dir) throws IOException, ParseException {
        return Arrays.stream(searcher.searchField(Const.tagFileProperty, "/.+/", dir, Searcher.MAX_DOC))
                .map(converter::convertDocToFile)
                .filter(distinctByFileProperty(File::getTag))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private <T> Predicate<T> distinctByFileProperty(Function<? super T, ?> keyExtractor) {
        Set<Object> unique = ConcurrentHashMap.newKeySet();
        return t -> unique.add(keyExtractor.apply(t));
    }
}
