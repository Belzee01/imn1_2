import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import poisson.WariantA;

import java.util.stream.Stream;

public class Main {

    public static void executeWariantA() {
        /***
         * Wariant A
         */
        final WariantA.Box box = new WariantA.Box(
                new WariantA.Range(-72.0, 72.0),
                new WariantA.Range(-72.0, 72.0)
        );

        WariantA[] wariantAS = { new WariantA(0.75, 0.75, box, 0.6),
                new WariantA(0.75, 0.75, box, 0.8),
                new WariantA(0.75, 0.75, box, 1.0),
                new WariantA(0.75, 0.75, box, 1.5),
                new WariantA(0.75, 0.75, box, 1.9),
                new WariantA(0.75, 0.75, box, 1.95),
                new WariantA(0.75, 0.75, box, 1.99)};

        Stream.of(wariantAS).parallel().forEach(r -> {
            System.out.println("\nIntegral value : " +  r.calculateIntegral());

            CustomFileWriter.writeToFile(r.getIntegralValueByIterations(), "wariant1aOmega" + r.getOmega() + ".dat");
        });

        WariantA wariantA = new WariantA(0.75, 0.75, box,1.0, 32);

        CustomFileWriter.writeToFile(wariantA.calculateIntegralFromMultiWireRelaxation(), "wariant1a_calka.dat");

        wariantA.fillDensityMatrix();
        CustomFileWriter.writeToFile(new AdvancedOutputFile(
                wariantA.getDensity(), wariantA.getBindingBox(), wariantA.getDeltaX(),"wariant1a_density.dat"));

        wariantA.getPotentialByJumpValues().forEach((key, value) -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(
                    value, wariantA.getBindingBox(), wariantA.getDeltaX(),"wariant1a_pot_k"+ key +".dat"));
        });
    }

    public static void main(String[] args) {

        executeWariantA();

//        /***
//         * Wariant B
//         */
//        final WariantA.Box box = new WariantA.Box(
//                new WariantA.Range(-72.0, 72.0),
//                new WariantA.Range(-72.0, 72.0)
//        );
//        WariantA wariantA = new WariantA(0.01, 0.01, box,1.0, 1);
    }
}
