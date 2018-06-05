package pl.edu.agh.ki.io.forganizer.utils;

import java.util.regex.Pattern;

public final class PathConverter {

    private final static Pattern regex = Pattern.compile("[^\\\\/:*?\"<>|\\r\\n]+$");

    public static String getPathForExplorer(String path){
        return regex.matcher(path).replaceAll("");
    }
}
