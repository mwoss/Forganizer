package pl.edu.agh.ki.io.forganizer.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class File extends RecursiveTreeObject<File>{

    /* In tag and comment getters, setters fields are checked if they are not null,
     because those fields are not initialized on object init */

    private StringProperty name;
    private StringProperty path;
    private StringProperty comment;
    private StringProperty tag;

    public File(String name, String path) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
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
        if (comment == null)
            return null;
        return comment.get();
    }

    public StringProperty getCommentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        if (this.comment == null)
            this.comment = new SimpleStringProperty(comment);
        else
            this.comment.setValue(comment);
    }

    public String getTag() {
        if (tag == null)
            return null;
        return tag.get();
    }

    public StringProperty getTagProperty() {
        return tag;
    }

    public void setTag(String tag) {
        if (this.tag == null)
            this.tag = new SimpleStringProperty(tag);
        else
            this.tag.set(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (!name.get().equals(file.getName())) return false;
        if (!path.get().equals(file.getPath())) return false;
        if (comment != null ? !comment.get().equals(file.getComment()) : file.getComment() != null) return false;
        return tag != null ? tag.get().equals(file.getTag()) : file.getTag() == null;
    }

    @Override
    public int hashCode() {
        int result = name.get().hashCode();
        result = 31 * result + path.get().hashCode();
        result = 31 * result + (comment != null ? comment.get().hashCode() : 0);
        result = 31 * result + (tag != null ? tag.get().hashCode() : 0);
        return result;
    }
}
