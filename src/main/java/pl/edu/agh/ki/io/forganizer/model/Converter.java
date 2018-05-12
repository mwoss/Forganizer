package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;

public class Converter {

    public File convertDocToFile(Document doc) {
        String name = doc.get("name");
        String path = doc.get("path");
        File file = new File(name, path);
        if (doc.get("comment") != null) file.setComment(doc.get("comment"));
        if (doc.get("tag") != null) file.setTag(doc.get("tag"));
        return file;
    }

    public Document convertFileToDoc(File file) {
        Document document = new Document();
        document.add(new StringField("name", file.getName(), Field.Store.YES));
        document.add(new StringField("path", file.getPath(), Field.Store.YES));
        if (file.getTag() != null) {

            document.add(new StringField("tag", file.getTag(), Field.Store.YES));
        }
        if (file.getComment() != null) document.add(new StringField("comment", file.getComment(), Field.Store.YES));

        return document;
    }
}
