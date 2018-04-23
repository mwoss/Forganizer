package pl.edu.agh.ki.io.forganizer.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class Indexer {

    private String indexPath = "index";
    private boolean updateIndex = true;
    private Analyzer analyzer;

    public Indexer(String indexPath, String analyzerLanguage) {
        this.indexPath = indexPath;
        if (analyzerLanguage.equals("english")) {
            analyzer = new StandardAnalyzer();
        } else if (analyzerLanguage.equals("polish")) {
            analyzer = new MorfologikAnalyzer();
        }
    }

    public void addFile(String fileName, String filePath) {


    }
}
