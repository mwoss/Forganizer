package pl.edu.agh.ki.io.forganizer.model;

public class File {
    private String name;
    private String path;
    private String comment;
    private String tag;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public File withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public File withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
