package pl.edu.agh.ki.io.forganizer.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class File extends RecursiveTreeObject<File> {
    private StringProperty name;
    private StringProperty path;
    private StringProperty comment;
    private StringProperty tag;

    public File(String name, String path) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
        this.comment = new SimpleStringProperty();
        this.tag = new SimpleStringProperty();
    }

    public File withComment(String comment) {
        this.comment = new SimpleStringProperty(comment);
        return this;
    }

    public File withTag(String tag) {
        this.tag = new SimpleStringProperty(tag);
        return this;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty getPathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getComment() {
        if(comment == null)
            return null;
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getTag() {
        if (tag == null)
            return null;
        return tag.get();
    }


    public void setTag(String tag) {
        this.tag.set(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (!name.equals(file.name)) return false;
        if (!path.equals(file.path)) return false;
        if (comment != null ? !comment.equals(file.comment) : file.comment != null) return false;
        return tag != null ? tag.equals(file.tag) : file.tag == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
