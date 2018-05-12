package pl.edu.agh.ki.io.forganizer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexerTest {

    @Test
    void addFileToRAMDirectoryTest() {
        Indexer indexer = new Indexer("test", Language.ENGLISH);
        String fileName = "file-with-long-name.txt";
        String filePath = "/home/yurii/tmp/" + fileName;
        Document document = new Document();
        document.add(new StringField("name", fileName, Field.Store.YES));
        document.add(new StringField("path", filePath, Field.Store.YES));
        Directory dir = new RAMDirectory();
        try {
            indexer.addDoc(document, dir);
            DirectoryReader dirReader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(dirReader);
            Query query = new MatchAllDocsQuery();
            ScoreDoc[] hits = searcher.search(query, 100).scoreDocs;
            assertEquals(1, hits.length);
            Document doc = searcher.doc(hits[0].doc);
            assertEquals(fileName, doc.get("name"));
            assertEquals(filePath, doc.get("path"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
