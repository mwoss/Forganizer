package pl.edu.agh.ki.io.forganizer.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import pl.edu.agh.ki.io.forganizer.utils.Const;

import java.io.IOException;

public class Indexer {

    private String indexPath;
    private Analyzer analyzer;

    public Indexer(String indexPath, Language language) {
        this.indexPath = indexPath;
        switch (language) {
            case POLISH:
                analyzer = new MorfologikAnalyzer();
                break;
            default:
                analyzer = new StandardAnalyzer();
                break;
        }
    }
    // TODO
    // dir = FSDirectory.open(Paths.get(indexPath));
    // dir = new RAMDirectory();

    private IndexWriter prepareIndexWriter(Directory dir) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        return new IndexWriter(dir, config);
    }

    public void addDoc(Document doc, Directory dir) throws IOException {
        IndexWriter indexWriter = prepareIndexWriter(dir);
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    public void removeDoc(Document doc, Directory dir) throws IOException {
        IndexWriter indexWriter = prepareIndexWriter(dir);
        Term[] terms = {new Term(Const.pathFileProperty, doc.get(Const.pathFileProperty))};
        indexWriter.deleteDocuments(terms);
        indexWriter.close();
    }

    public void updateDoc(Document doc, Directory dir) throws IOException {
        IndexWriter indexWriter = prepareIndexWriter(dir);
        Term term = new Term(Const.pathFileProperty, doc.get(Const.pathFileProperty));
        indexWriter.updateDocument(term, doc);
        indexWriter.close();
    }
}
