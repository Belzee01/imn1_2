import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import poisson.WariantA;
import poisson.WariantB;

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

        WariantA[] wariantAS = {new WariantA(0.75, 0.75, box, 0.6),
                new WariantA(0.75, 0.75, box, 0.8),
                new WariantA(0.75, 0.75, box, 1.0),
                new WariantA(0.75, 0.75, box, 1.5),
                new WariantA(0.75, 0.75, box, 1.9),
                new WariantA(0.75, 0.75, box, 1.95),
                new WariantA(0.75, 0.75, box, 1.99)};

        Stream.of(wariantAS).parallel().forEach(r -> {
            System.out.println("\nIntegral value : " + r.calculateIntegral());

            CustomFileWriter.writeToFile(r.getIntegralValueByIterations(), "wariant1aOmega" + r.getOmega() + ".dat");
        });

        WariantA wariantA = new WariantA(0.75, 0.75, box, 1.0, 32);

        CustomFileWriter.writeToFile(wariantA.calculateIntegralFromMultiWireRelaxation(), "wariant1a_calka.dat");

        wariantA.fillDensityMatrix();
        CustomFileWriter.writeToFile(new AdvancedOutputFile(
                wariantA.getDensity(), wariantA.getBindingBox(), wariantA.getDeltaX(), "wariant1a_density.dat"));

        wariantA.getPotentialByJumpValues().forEach((key, value) -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(
                    value, wariantA.getBindingBox(), wariantA.getDeltaX(), "wariant1a_pot_k" + key + ".dat"));
        });
    }

    public static void executeWariantB() {
        WariantB.Points point1 = new WariantB.Points(0.4, 0.64);
        WariantB.Points point2 = new WariantB.Points(0.88, 0.64);
        WariantB.Dirichlet dirichlet = new WariantB.Dirichlet(0.0, 0.0, -0.1, 0.1);

        final Integer numberOfPoints = 128;

        WariantB wariantB = new WariantB(0.01, 0.01, numberOfPoints, 1.0, 1, point1, point2, 0.051, dirichlet);

        /**
         * Relaksacja globalna
         */
        WariantB[] wariantBS1 = {
                new WariantB(0.01, 0.01, numberOfPoints, 0.1, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.2, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.3, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.4, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.5, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.6, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.7, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.8, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 0.9, 1, point1, point2, 0.051, dirichlet)
        };

        Stream.of(wariantBS1).parallel().forEach(WariantB::examineIntegralGlobal);
        Stream.of(wariantBS1).parallel().forEach(w -> {
            CustomFileWriter.writeToFile(w.getIntegralByIteration(), "warB_integral_glob_omega"+w.getOmega()+".dat");
        });
        Stream.of(wariantBS1).parallel().forEach(w -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(w.getPotential(), numberOfPoints, "warB_pot_omega"+w.getOmega()+".dat"));
        });
        CustomFileWriter.writeToFile(new AdvancedOutputFile(wariantBS1[0].getDensity(), numberOfPoints, "warB_density.dat"));


        /**
         * Relaksacja lokalna
         */
        WariantB[] wariantBS2 = {
                new WariantB(0.01, 0.01, numberOfPoints, 1.1, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.2, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.3, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.4, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.5, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.6, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.7, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.8, 1, point1, point2, 0.051, dirichlet),
                new WariantB(0.01, 0.01, numberOfPoints, 1.9, 1, point1, point2, 0.051, dirichlet)
        };

        Stream.of(wariantBS2).parallel().forEach(WariantB::examineIntegralLocal);
        Stream.of(wariantBS2).parallel().forEach(w -> {
            CustomFileWriter.writeToFile(w.getIntegralByIteration(), "warB_integral_lok_omega"+w.getOmega()+".dat");
        });
        Stream.of(wariantBS2).parallel().forEach(w -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(w.getPotential(), numberOfPoints, "warB_pot_omega"+w.getOmega()+".dat"));
        });

        /**
         * Relaksacja wielosiatkowa
         */
        WariantB zadanie2 = new WariantB(0.01, 0.01, numberOfPoints, 1.9, 16, point1, point2, 0.051, dirichlet);
        String result = zadanie2.multiWireLocalRelaxation();
        CustomFileWriter.writeToFile(result, "multiwire_calka.dat");

        zadanie2.getPotentialByJumpValues().forEach((key, value) -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(
                    value, numberOfPoints, "warB_mw_k" + key + ".dat"));
        });
    }

    public static void main(String[] args) {

//        executeWariantA();
        executeWariantB();
    }
}
