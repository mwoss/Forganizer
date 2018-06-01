package pl.edu.agh.ki.io.forganizer.utils;

public final class Const {

    /* Store size units for Size Converter*/
    public static final String[] unitsArray = {"kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    /* Store index path for indexer class */
    public static final String pathIndex = "index";

    /* File name property */
    public static final String fileNameProperty = "name";

    /* File path property */
    public static final String filePathProperty = "path";

    /* File size property */
    public static final String fileSizeProperty = "size";

    /* File type property */
    public static final String fileTypeProperty = "fileType";

    /* File tag property */
    public static final String tagFileProperty = "tag";

    /* File comment property */
    public static final String commentFileProperty = "comment";

    /* All files view ID in side menu */
    public static final String allFilesItemID = "allFiles";

    private Const(){
        throw new AssertionError();
    }
}
