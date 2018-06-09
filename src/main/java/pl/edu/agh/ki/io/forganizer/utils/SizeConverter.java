package pl.edu.agh.ki.io.forganizer.utils;

public final class SizeConverter {

    public static String getReadableFileSize(Long fileSize) {
        int unit = -1;
        do {
            fileSize /= 1024;
            unit++;
        } while (fileSize > 1024);
        String size = String.format("%.1f", Math.max(fileSize, 0.1));
        return size + Const.unitsArray[unit];
    }
}
