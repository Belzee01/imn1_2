package poisson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WariantB {

    private int numberOfPoints;
    private double deltaX;
    private double deltaY;
    private double omega;
    private int jump;

    private double[][] potential;
    private double[][] density;

    private final double charge;
    private Dirichlet dirichlet;
    private Points point1;
    private Points point2;
    private double R;

    private HashMap<String, double[][]> potentialByJumpValues;

    private WariantA.IterationIntegralContainer integralByIteration;

    public WariantB(Double deltaX, Double deltaY, int numberOfPoints, double omega, int jump, Points point1, Points point2, double R, Dirichlet dirichlet) {
        this.numberOfPoints = numberOfPoints + 1;
        this.omega = omega;
        this.jump = jump;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

        this.point1 = point1;
        this.point2 = point2;
        this.dirichlet = dirichlet;
        this.R = R;

        this.charge = 80.0;

        this.potential = new double[this.numberOfPoints][this.numberOfPoints];
        this.density = new double[this.numberOfPoints][this.numberOfPoints];

        this.integralByIteration = new WariantA.IterationIntegralContainer();
        this.potentialByJumpValues = new HashMap<>();

        fillInitialPotential();
        fillDensity();
    }

    private void fillInitialPotential() {
        for (int i = 0; i < this.numberOfPoints; i++) {
            for (int j = 0; j < this.numberOfPoints; j++) {
                if (i == 0) {
                    this.potential[i][j] = dirichlet.getLeftEdge();
                }
                if (i == this.numberOfPoints - 1) {
                    this.potential[i][j] = dirichlet.getRightEdge();
                }
                if (j == 0) {
                    this.potential[i][j] = dirichlet.getLowerEdge();
                }
                if (j == this.numberOfPoints - 1) {
                    this.potential[i][j] = dirichlet.getUpperEdge();
                }
            }
        }
    }

    private void fillDensity() {
        for (int i = 0; i < this.numberOfPoints; i++) {
            for (int j = 0; j < this.numberOfPoints; j++) {
                double x = i * deltaX;
                double y = j * deltaY;
                if (checkIfInCircleRange(x, y, point1)) {
                    this.density[i][j] = this.charge;
                }
                if (checkIfInCircleRange(x, y, point2)) {
                    this.density[i][j] = -this.charge;
                }
            }
        }
    }

    private boolean checkIfInCircleRange(double x, double y, Points points) {
        return (Math.pow(x - points.getX(), 2.0) + Math.pow(y - points.getY(), 2.0) <= this.R);
    }

    public void calculateGlobalRelaxation() {

        double[][] tempPotential = new double[this.numberOfPoints][this.numberOfPoints];

        for (int i = jump; i < this.numberOfPoints - jump; i += jump) {
            for (int j = jump; j < this.numberOfPoints - jump; j += jump) {
                tempPotential[i][j] = (1.0 - omega) * potential[i][j] +
                        omega * ((potential[i + jump][j] +
                                potential[i - jump][j] +
                                potential[i][j + jump] +
                                potential[i][j - jump] +
                                (jump * jump * deltaY * deltaX) * density[i][j]) / 4.0);
            }
        }
        potential = Arrays.stream(tempPotential).map(double[]::clone).toArray(double[][]::new);
    }

    public void calculateLocalRelaxation() {
        for (int i = jump; i < this.numberOfPoints - jump; i += jump) {
            for (int j = jump; j < this.numberOfPoints - jump; j += jump) {
                this.potential[i][j] = (1.0 - omega) * potential[i][j] +
                        omega * ((potential[i + jump][j] +
                                potential[i - jump][j] +
                                potential[i][j + jump] +
                                potential[i][j - jump] +
                                (jump * jump * deltaY * deltaX) * density[i][j]) / 4.0);
            }
        }
    }

    private double calculateIntegral() {
        double a = 0.0;
        for (int i = 0; i < this.numberOfPoints - jump; i += jump) {
            for (int j = 0; j < this.numberOfPoints - jump; j += jump) {
                a += (jump * jump * deltaX * deltaY) * (0.5 * (Math.pow(differentialForX(i, j), 2.0) + Math.pow(differentialForY(i, j), 2.0)) - this.density[i][j] * this.potential[i][j]);
            }
        }
        return a;
    }

    public double examineIntegralGlobal() {
        double currentIntegralValue = calculateIntegral();
        calculateGlobalRelaxation();

        this.integralByIteration.add(0, currentIntegralValue);

        double diff = 0.0;
        int k = 1;
        do {
            double newIntegralValue = calculateIntegral();
            this.integralByIteration.add(k, newIntegralValue);

            diff = Math.abs((currentIntegralValue - newIntegralValue) / currentIntegralValue);
            currentIntegralValue = newIntegralValue;
            calculateGlobalRelaxation();
            k++;
        } while (diff >= 10e-12);

        return currentIntegralValue;
    }

    public double examineIntegralLocal() {
        double currentIntegralValue = calculateIntegral();
        calculateLocalRelaxation();
        this.integralByIteration.add(0, currentIntegralValue);

        double diff = 0.0;
        int k = 1;
        if (this.integralByIteration.getMyPairs().size() != 0)
            k = this.integralByIteration.getMyPairs().get(this.integralByIteration.getMyPairs().size()-1).getIteration();
        do {
            double newIntegralValue = calculateIntegral();
            this.integralByIteration.add(k, newIntegralValue);

            diff = Math.abs((newIntegralValue - currentIntegralValue) / currentIntegralValue);
            currentIntegralValue = newIntegralValue;
            calculateLocalRelaxation();
            k++;
        } while (diff >= 10e-12);

        return currentIntegralValue;
    }

    private void evaluateNewWirePotential() {
        for (int i = jump; i < numberOfPoints - jump; i += jump) {
            for (int j = jump; j < numberOfPoints - jump; j += jump) {

                this.potential[i + jump / 2][j + jump / 2] =
                        (potential[i][j] + potential[i + jump][j] +
                                potential[i][j + jump] + potential[i + jump][j + jump]) / 4.0;

                potential[i][j + jump / 2] = (potential[i][j] + potential[i][j + jump]) / 2.0;
                potential[i + jump / 2][j] = (potential[i][j] + potential[i + jump][j]) / 2.0;
                potential[i + jump][j + jump / 2] = (potential[i + jump][j] + potential[i + jump][j + jump]) / 2.0;
                potential[i + jump / 2][j + jump] = (potential[i][j + jump] + potential[i + jump][j + jump]) / 2.0;
            }
        }
    }

    private void evaluateNewDensity() {
        for (int i = jump; i < numberOfPoints-jump; i++) {
            for (int j = jump; j < numberOfPoints-jump; j++) {
                double weight = calculateWeight(i, j);
                double integral = calculateIntegralAtPoint(i, j);
                this.density[i][j] = integral / weight;
            }
        }
    }

    private double calculateWeight(int x, int y) {
        double weight = 0.0;
        for (int i = x - jump / 2; i < x + jump / 2; i++) {
            for (int j = y - jump / 2; j < y + jump / 2; j++) {
                double wI = 1.0;
                double wJ = 1.0;
                if (i == x) {
                    wI = 0.5;
                }
                if (j == y) {
                    wJ = 0.5;
                }
                weight += wI * wJ;
            }
        }
        return weight;
    }

    private double calculateIntegralAtPoint(int x, int y) {
        double integralAtPoint = 0.0;
        for (int i = x - jump / 2; i < x + jump / 2; i++) {
            for (int j = y - jump / 2; j < y + jump / 2; j++) {
                double wI = 1.0;
                double wJ = 1.0;
                if (i == x) {
                    wI = 0.5;
                }
                if (j == y) {
                    wJ = 0.5;
                }
                integralAtPoint += wI * wJ * this.density[i][j];
            }
        }
        return integralAtPoint;
    }

    public String multiWireLocalRelaxation() {
        List<WariantA.IterationIntegralContainer> iterationIntegralContainers = new ArrayList<>();

        double current = 0.0;
        do {
            evaluateNewDensity();
            examineIntegralLocal();
            double[][] temp = Arrays.stream(this.potential).map(double[]::clone).toArray(double[][]::new);
            this.potentialByJumpValues.put(String.valueOf(this.jump), temp);
            iterationIntegralContainers.add(this.integralByIteration);
            evaluateNewWirePotential();
            this.jump = jump / 2;
        } while (this.jump != 1);

        examineIntegralLocal();
        double[][] temp = Arrays.stream(this.potential).map(double[]::clone).toArray(double[][]::new);
        this.potentialByJumpValues.put(String.valueOf(this.jump), temp);
        iterationIntegralContainers.add(this.integralByIteration);
        return convertList(iterationIntegralContainers);
    }

    private String convertList(List<WariantA.IterationIntegralContainer> iterationIntegralContainers) {
        StringBuilder sb = new StringBuilder();

        for (WariantA.IterationIntegralContainer i : iterationIntegralContainers) {
            sb.append(i).append("\n");
        }

        return sb.toString();
    }

    private double differentialForX(int i, int j) {
        return (this.potential[i + jump][j] - this.potential[i][j]) / (jump * deltaX);
    }

    private double differentialForY(int i, int j) {
        return (this.potential[i][j + jump] - this.potential[i][j]) / (jump * deltaY);
    }

    public WariantA.IterationIntegralContainer getIntegralByIteration() {
        return integralByIteration;
    }

    public HashMap<String, double[][]> getPotentialByJumpValues() {
        return potentialByJumpValues;
    }

    public double[][] getPotential() {
        return potential;
    }

    public double[][] getDensity() {
        return density;
    }

    public double getOmega() {
        return omega;
    }

    public static class Points {
        private double x;
        private double y;

        public Points(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public static class Dirichlet {
        private double upperEdge;
        private double lowerEdge;
        private double rightEdge;
        private double leftEdge;

        public Dirichlet(double upperEdge, double lowerEdge, double rightEdge, double leftEdge) {
            this.upperEdge = upperEdge;
            this.lowerEdge = lowerEdge;
            this.rightEdge = rightEdge;
            this.leftEdge = leftEdge;
        }

        public double getUpperEdge() {
            return upperEdge;
        }

        public double getLowerEdge() {
            return lowerEdge;
        }

        public double getRightEdge() {
            return rightEdge;
        }

        public double getLeftEdge() {
            return leftEdge;
        }
    }

}
