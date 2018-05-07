package pl.edu.agh.ki.io.forganizer;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.io.forganizer.search.FolderType;
import pl.edu.agh.ki.io.forganizer.search.Indexer;
import pl.edu.agh.ki.io.forganizer.search.Language;

import java.io.IOException;

public class IndexerTest {

    @Test
    void addFileTest() {
        Indexer indexer = new Indexer("test", Language.ENGLISH);
        try {
            indexer.addFile(
                    "file1.txt",
                    "/home/yurii/tmp/file1.txt",
                    FolderType.RAM);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
