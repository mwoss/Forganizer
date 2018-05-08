package pl.edu.agh.ki.io.forganizer;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.io.forganizer.search.Indexer;

public class IndexerTest {

    @Test
    void addFileTest() {
        Indexer indexer = new Indexer("test", "english");
        try {
            indexer.addFile("file1.txt", "/home/yurii/tmp/file1.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
