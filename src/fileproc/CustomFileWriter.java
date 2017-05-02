package fileproc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CustomFileWriter {

    public static void writeToFile(AdvancedOutputFile advancedOutputFile) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(advancedOutputFile.getFilename()))) {
            bw.write(advancedOutputFile.toString());

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
