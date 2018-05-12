package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;
import pl.edu.agh.ki.io.forganizer.search.Searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        indexer.addFile(converter.convertFileToDoc(file), dir);
    }

    public void updateFile(File file) {

    }

    public void removeFile(File file) {

    }

    List<File> getAllFiles() {
        List<File> allFiles = null;
        try (Directory dir = FSDirectory.open(Paths.get(path));
            IndexReader reader = DirectoryReader.open(dir)) {
            int n = reader.maxDoc();
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new MatchAllDocsQuery();
            ScoreDoc[] hits = searcher.search(query, n).scoreDocs;
            allFiles = Arrays.stream(hits).map(e -> {
                try {
                    return converter.convertDocToFile(searcher.doc(e.doc));
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allFiles;
    }


}
