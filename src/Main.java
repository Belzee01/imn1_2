import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import poisson.Relaxation;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Relaxation relaxation = new Relaxation(0.75, 0.75,
                new Relaxation.Box(
                        new Relaxation.Range(-72.0, 72.0),
                        new Relaxation.Range(-72.0, 72.0)
                ),
                1.0, 32
        );

        System.out.println("\nIntegral value : " +  relaxation.calculateIntegralFromMultiWireRelaxation());

//        CustomFileWriter.writeToFile(new AdvancedOutputFile(relaxation, "wariant1a.dat"));

        relaxation.getPotentialByJumpValues().forEach((key, value) -> {
            CustomFileWriter.writeToFile(new AdvancedOutputFile(
                    value, relaxation.getBindingBox(), relaxation.getDeltaX(),"wariant1a_pot_k"+ key +".dat"));
        });
    }
}
