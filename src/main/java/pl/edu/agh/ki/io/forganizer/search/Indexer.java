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
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Indexer {

    private String indexPath;
    private Analyzer analyzer;

    public Indexer(String indexPath, Language analyzerLanguage) {
        this.indexPath = indexPath;
        switch (analyzerLanguage) {
            case POLISH:
                analyzer = new MorfologikAnalyzer();
                break;
            default:
                analyzer = new StandardAnalyzer();
                break;
        }
    }

    public void addFile(String fileName, String filePath, FolderType folderType) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        Directory dir;
        switch (folderType) {
            case FS:
                dir = FSDirectory.open(Paths.get(indexPath));
                break;
            default:
                dir = new RAMDirectory();
                break;
        }

        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(dir, config);

        Document doc = new Document();
        doc.add(new StringField("path", filePath, Field.Store.YES));
        doc.add(new StringField("name", fileName, Field.Store.YES));

        indexWriter.updateDocument(new Term("path", filePath), doc);
        indexWriter.close();
    }
}
