package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.io.forganizer.search.Language;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    private FileManager fileManager;

    @BeforeEach
    void init() {
        IndexWriterConfig conf = new IndexWriterConfig(
                new StandardAnalyzer());
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        try (Directory directory = FSDirectory.open(Paths.get("test"))) {

            IndexWriter indexWriter = new IndexWriter(directory, conf);
            indexWriter.deleteAll();
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileManager = new FileManager("test", Language.ENGLISH);

    }


    @Test
    void addFileTest() throws IOException {
        //Given
        String fileNameTest1 = "xD.txt";
        String fileNameTest2 = "xDD.txt";
        String filePathTest1 = "home/countess/Documents/test1";
        String filePathTest2 = "home/countess/Documents/test2";
        File fileTest1 = new File(fileNameTest1, filePathTest1);
        File fileTest2 = new File(fileNameTest2, filePathTest2);
        Directory dir = new RAMDirectory();
        //When
        try {
            fileManager.addFile(fileTest1, dir);
            fileManager.addFile(fileTest2, dir);
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }

        //Then
        try (DirectoryReader dirReader = DirectoryReader.open(dir)) {
            IndexSearcher searcher = new IndexSearcher(dirReader);
            Query query = new MatchAllDocsQuery();
            ScoreDoc[] hits = searcher.search(query, 100).scoreDocs;
            assertEquals(2, hits.length);
            Document doc1 = searcher.doc(hits[0].doc);
            Document doc2 = searcher.doc(hits[1].doc);
            assertEquals(fileNameTest1, doc1.get("name"));
            assertEquals(fileNameTest2, doc2.get("name"));
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }


    }

    @Test
    void updateFile() {
        assert true;
    }

    @Test
    void removeFile() {
        assert true;
    }

    @Test
    void getAllFilesTest() {
        //Given
        File file1 = new File("xDD", "home/countess/Documents/test1")
                .withComment("lol");
        File file2 = new File("important-file.txt", "home/countess/Documents/test2/more/important");
        File file3 = new File("my-picture.png", "home/countess/Documents/pictures")
                .withTag("picture");
        File file4 = new File("my-picture2.png", "home/countess/Documents/pictures")
                .withTag("picture");
        File file5 = new File("my-picture3.png", "home/countess/Documents/pictures")
                .withTag("picture");
        File file6 = new File("script.sh", "home/countess/Documents/dev/scripts")
                .withTag("scripts")
                .withComment("to initialize sth");
        List<File> expectedFiles = Arrays.asList(file1, file2, file3, file4, file5, file6);


        try (Directory directory = FSDirectory.open(Paths.get("test"))) {
            fileManager.addFile(file1, directory);
            fileManager.addFile(file2, directory);
            fileManager.addFile(file3, directory);
            fileManager.addFile(file4, directory);
            fileManager.addFile(file5, directory);
            fileManager.addFile(file6, directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When
        List<File> files = null;
        try (Directory directory = FSDirectory.open(Paths.get("test"))) {
            files = fileManager.getAllFiles(directory);
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
        //Then
        assertTrue(expectedFiles.containsAll(files));
        assertTrue(files.containsAll(expectedFiles));

    }
}