package pl.edu.agh.ki.io.forganizer.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
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
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);

        Document document = new Document();
        document.add(new StringField("path", filePath, Field.Store.YES));
        document.add(new StringField("name", fileName, Field.Store.YES));

        indexWriter.updateDocument(new Term("path", filePath), document);
        indexWriter.close();

    }
}
