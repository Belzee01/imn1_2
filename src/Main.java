import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import poisson.Relaxation;

public class Main {

    public static void main(String[] args) {

        Relaxation relaxation = new Relaxation(0.75, 0.75,
                new Relaxation.Box(
                        new Relaxation.Range(-72.0, 72.0),
                        new Relaxation.Range(-72.0, 72.0)
                ),
                1.95
        );

        System.out.println("\nIntegral value : " +  relaxation.calculateIntegral());

        CustomFileWriter.writeToFile(new AdvancedOutputFile(relaxation, "wariant1a.dat"));

        System.out.println("Hello World!");
    }
}
