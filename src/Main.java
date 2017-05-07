import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import poisson.Relaxation;

import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {


        /***
         * Wariant A
         */
//        final Relaxation.Box box = new Relaxation.Box(
//                new Relaxation.Range(-72.0, 72.0),
//                new Relaxation.Range(-72.0, 72.0)
//        );
//
//        Relaxation[] relaxations = { new Relaxation(0.75, 0.75, box, 0.6),
//                new Relaxation(0.75, 0.75, box, 0.8),
//                new Relaxation(0.75, 0.75, box, 1.0),
//                new Relaxation(0.75, 0.75, box, 1.5),
//                new Relaxation(0.75, 0.75, box, 1.9),
//                new Relaxation(0.75, 0.75, box, 1.95),
//                new Relaxation(0.75, 0.75, box, 1.99)};
//
//        Stream.of(relaxations).parallel().forEach(r -> {
//            System.out.println("\nIntegral value : " +  r.calculateIntegral());
//
//            CustomFileWriter.writeToFile(r.getIntegralValueByIterations(), "wariant1aOmega" + r.getOmega() + ".dat");
//        });
//
//        Relaxation relaxation = new Relaxation(0.75, 0.75, box,1.0, 32);
//
//        CustomFileWriter.writeToFile(relaxation.calculateIntegralFromMultiWireRelaxation(), "wariant1a_calka.dat");
//
//        relaxation.fillDensityMatrix();
//        CustomFileWriter.writeToFile(new AdvancedOutputFile(
//                relaxation.getDensity(), relaxation.getBindingBox(), relaxation.getDeltaX(),"wariant1a_density.dat"));
//
//        relaxation.getPotentialByJumpValues().forEach((key, value) -> {
//            CustomFileWriter.writeToFile(new AdvancedOutputFile(
//                    value, relaxation.getBindingBox(), relaxation.getDeltaX(),"wariant1a_pot_k"+ key +".dat"));
//        });


        /***
         * Wariant B
         */
        final Relaxation.Box box = new Relaxation.Box(
                new Relaxation.Range(-72.0, 72.0),
                new Relaxation.Range(-72.0, 72.0)
        );
        Relaxation relaxation = new Relaxation(0.01, 0.01, box,1.0, 1);
    }
}
