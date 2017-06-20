
package com.nalashaa.poc;

/*import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
*/
public class SaveFile {

    /*public static void main(String[] args) throws IOException {

        String seperator = "\\";
        String basepath = "D:";
        String provider = "Dr.Prasad N A";
        String measure = "68v3";

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY hh-mm-ss");
        String time = dateFormat.format(now);

        String folderPath = basepath + seperator + provider + seperator + time + seperator + "cat-1" + seperator + measure;

        boolean isDirectoryCreated = createDirectory(folderPath);

        writeToTextFile(folderPath + seperator + "abc.xml", "hello");
    }

    public static boolean createDirectory(String directoryPath) {
        File file = new File(directoryPath);
        boolean isDirectoryCreated = false;
        if (!file.exists()) {
            isDirectoryCreated = file.mkdirs();
        }
        return isDirectoryCreated;
    }

    public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }*/
}
