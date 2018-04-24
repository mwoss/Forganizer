package pl.edu.agh.ki.io.forganizer.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Indexer {

    private String indexPath = "index";
    private Analyzer analyzer;

    public Indexer(String indexPath, String analyzerLanguage) {
        this.indexPath = indexPath;
        if (analyzerLanguage.equals("english")) {
            analyzer = new StandardAnalyzer();
        } else if (analyzerLanguage.equals("polish")) {
            analyzer = new MorfologikAnalyzer();
        }
    }

    public void addFile(String fileName, String filePath) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));

    }
}
