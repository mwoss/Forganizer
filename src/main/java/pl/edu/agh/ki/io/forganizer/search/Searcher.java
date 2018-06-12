package pl.edu.agh.ki.io.forganizer.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public class Searcher {

    private Analyzer analyzer;

    public static final int MAX_DOC = Integer.MAX_VALUE - 128;

    public Searcher(String indexPath, Language language) {
        switch (language) {
            case POLISH:
                analyzer = new MorfologikAnalyzer();
                break;
            default:
                analyzer = new StandardAnalyzer();
                break;
        }
    }

    public Document[] searchField(String fieldName,
                                  String queryString,
                                  Directory dir,
                                  int resultsNum) throws IOException, ParseException {
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        QueryParser queryParser = new QueryParser(fieldName, analyzer);
        Query query = queryParser.parse(queryString);
        ScoreDoc[] hits = indexSearcher.search(query, resultsNum).scoreDocs;
        Document[] results = new Document[hits.length];
        for (int i = 0; i < hits.length; i++) {
            results[i] = indexSearcher.doc(hits[i].doc);
        }
        return results;
    }

    public Document[] getAllDocs(Directory dir) throws IOException {
        DirectoryReader dirReader = DirectoryReader.open(dir);
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(dirReader);
        Query query = new MatchAllDocsQuery();
        ScoreDoc[] hits = searcher.search(query, indexReader.numDocs()).scoreDocs;
        Document[] docs = new Document[hits.length];
        for (int i = 0; i < hits.length; i++) {
            docs[i] = searcher.doc(hits[i].doc);
        }
        return docs;
    }
}
