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

    @Override
    public String toString() {
        return this.path + "/" + this.name;
    }
}
