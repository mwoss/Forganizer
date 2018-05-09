package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.document.Document;

public class Converter {

    public File convertDocToFile(Document doc) {
        String name = doc.get("name");
        String path = doc.get("path");
        File file = new File(name, path);
        if (doc.get("comment") != null) file.setComment(doc.get("comment"));
        if (doc.get("tag") != null) file.setComment(doc.get("tag"));
        return file;
    }

}
