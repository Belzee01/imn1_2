package fileproc;

import poisson.Relaxation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static poisson.Relaxation.*;

public class CustomFileWriter {

    public static void writeToFile(AdvancedOutputFile advancedOutputFile) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(advancedOutputFile.getFilename()))) {
            bw.write(advancedOutputFile.toString());

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(IterationIntegralContainer myPairs, String filename) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(myPairs.toString());

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String data, String filename) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(data);

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
