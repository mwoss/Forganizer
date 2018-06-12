package pl.edu.agh.ki.io.forganizer.utils;

public final class Const {

    /* Store size units for Size Converter*/
    public static final String[] unitsArray = {"kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    public static String pathIndex = "index";

    public static void setPathIndex(String pathIndex) {
        Const.pathIndex = pathIndex;
    }

    public static final String fileNameProperty = "name";

    public static final String filePathProperty = "path";

    public static final String fileSizeProperty = "size";

    public static final String fileTypeProperty = "fileType";

    public static final String tagFileProperty = "tag";

    public static final String commentFileProperty = "comment";

    public static final String allFilesItemID = "allFiles";

    private Const(){
        throw new AssertionError();
    }
}
