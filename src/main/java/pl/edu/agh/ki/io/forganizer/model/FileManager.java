package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.edu.agh.ki.io.forganizer.search.FolderType;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private Indexer indexer;
    private Converter converter;
    private final String path;

    public FileManager(String path, Language language) {
        this.path = path;
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
