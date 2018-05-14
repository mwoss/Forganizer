package pl.edu.agh.ki.io.forganizer.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import pl.edu.agh.ki.io.forganizer.utils.Const;

public class Converter {

    public File convertDocToFile(Document doc) {
        String name = doc.get(Const.nameFileProperty);
        String path = doc.get(Const.pathFileProperty);
        File file = new File(name, path);
        if (doc.get(Const.commentFileProperty) != null) file.setComment(doc.get(Const.commentFileProperty));
        if (doc.get(Const.tagFileProperty) != null) file.setTag(doc.get(Const.tagFileProperty));
        return file;
    }

    public Document convertFileToDoc(File file) {
        Document document = new Document();
        document.add(new StringField(Const.nameFileProperty, file.getName(), Field.Store.YES));
        document.add(new StringField(Const.pathFileProperty, file.getPath(), Field.Store.YES));
        if (file.getTag() != null) {

            document.add(new StringField(Const.tagFileProperty, file.getTag(), Field.Store.YES));
        }
        if (file.getComment() != null) document.add(new StringField(Const.commentFileProperty, file.getComment(), Field.Store.YES));

        return document;
    }
}
