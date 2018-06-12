package pl.edu.agh.ki.io.forganizer.utils;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsManager {

    public void setIndexFolder() throws AccessDeniedException {
        String indexPathStr = System.getProperty("user.home") + "/.forganizer/index";
        Path indexPath = Paths.get(indexPathStr);
        if (Files.notExists(indexPath)) {
            boolean created = new File(indexPathStr).mkdirs();
            if (!created) {
                throw new AccessDeniedException("failed to create index directory");
            }
        }
        Const.setPathIndex(indexPathStr);
    }
}
