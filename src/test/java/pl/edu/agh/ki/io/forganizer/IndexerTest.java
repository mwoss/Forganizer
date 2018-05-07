package pl.edu.agh.ki.io.forganizer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.io.forganizer.search.FolderType;
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
        try {
            Directory dir = indexer.addFile(fileName, filePath, FolderType.RAM);
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
