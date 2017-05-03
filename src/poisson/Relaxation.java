package poisson;

public class Relaxation {

    private Double deltaX;
    private Double deltaY;
    private Box bindingBox;
    private double[][] potential;
    private double omega;

    private int pointsCounterX;
    private int pointsCounterY;

    private int jump;

    public Relaxation(Double deltaX, Double deltaY, Box bindingBox, double omega) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.bindingBox = bindingBox;
        this.potential = new double[193][193];
        this.omega = omega;

        this.pointsCounterX = (int) ((this.bindingBox.getRangeX().getEnd() - this.bindingBox.getRangeX().getStart()) / deltaX) + 1;
        this.pointsCounterY = (int) ((this.bindingBox.getRangeY().getEnd() - this.bindingBox.getRangeY().getStart()) / deltaY) + 1;

        this.jump = 1;
    }

    /**
     * Konstruktor dla relaksacji wielosiatkowej
     *
     * @param deltaX
     * @param deltaY
     * @param bindingBox
     * @param omega
     * @param jump       okresla wielkosc skoku na siatce. Dla relakasacji punktowej jest to 1
     */
    public Relaxation(Double deltaX, Double deltaY, Box bindingBox, double omega, int jump) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.bindingBox = bindingBox;
        this.potential = new double[193][193];
        this.omega = omega;

        this.pointsCounterX = (int) ((this.bindingBox.getRangeX().getEnd() - this.bindingBox.getRangeX().getStart()) / deltaX) + 1;
        this.pointsCounterY = (int) ((this.bindingBox.getRangeY().getEnd() - this.bindingBox.getRangeY().getStart()) / deltaY) + 1;

        this.jump = jump;
    }

    public Double signalDensity(double x, double y) {
        return (1.0 / (Math.pow(25, 2.0) * Math.PI)) * Math.exp(-Math.pow(x / 25.0, 2.0) - Math.pow(y / 25.0, 2.0));
    }

    public void calculatePotential() {
        for (double i = this.bindingBox.getRangeX().getStart() + jump*deltaX; i <= this.bindingBox.getRangeX().getEnd() - jump * this.deltaX; i += jump * this.deltaX) {
            for (double j = this.bindingBox.getRangeY().getStart() + jump*deltaY; j <= this.bindingBox.getRangeY().getEnd() - jump * this.deltaY; j += jump * this.deltaY) {
                int indexX = getIndexX(i);
                int indexY = getIndexY(j);

                this.potential[indexX][indexY] = getPotentialAtPoint(i, j);
            }
        }
    }

    public double calculateIntegralAtIteration() {
        double a = 0.0;

        for (double i = this.bindingBox.getRangeX().getStart(); i <= this.bindingBox.getRangeX().getEnd() - jump * this.deltaX; i += jump * this.deltaX) {
            for (double j = this.bindingBox.getRangeY().getStart(); j <= this.bindingBox.getRangeY().getEnd() - jump * this.deltaY; j += jump * this.deltaY) {
                int indexX = getIndexX(i);
                int indexY = getIndexY(j);

                a += Math.pow(jump, 2.0) * this.deltaX * this.deltaY * (0.5 * (Math.pow(differentialForX(i, j), 2.0) + Math.pow(differentialForY(i, j), 2.0)) -
                         this.signalDensity(i, j) * this.potential[indexX][indexY]);
            }
        }
        calculatePotential();

        return a;
    }

    /**
     * Zwraca wartosc calki z pojedynczego oszacowania zbieznosci calki
     * Uzywac dla parametru k = 1 - lokalna relaksacja punktowa
     * @return
     */
    public double calculateIntegral() {
        double temp = calculateIntegralAtIteration();
        double diff = 0.0;

        do {
            double integralValue = calculateIntegralAtIteration();

            diff = Math.abs(integralValue - temp);
            temp = integralValue;
            System.out.println("Diff : " + diff);
        } while (diff > 0.00000001);

        return temp;
    }

    /**
     * Zwraca relaksacje wielosiatkowa, zmniejsza o polowe wartosc paramteru k do momentu k == 1
     * Uzywac dla lokalnej relaksacji wielosiatkowej k > 1
     * @return
     */
    public double calculateIntegralFromMultiWireRelaxation() {
        double current = calculateIntegral();

        do {
            this.jump = jump/2;
            evaluateNewWire();

            double integralForJump = calculateIntegral();

        }while (this.jump != 1);

        return current;
    }

    public void evaluateNewWire() {

    }

    private double getPotentialAtPoint(double x, double y) {
        int i = getIndexX(x);
        int j = getIndexY(y);
        return (1.0 - this.omega) * this.potential[i][j] +
                this.omega * (this.potential[i + this.jump][j] + potential[i - this.jump][j] + potential[i][j + this.jump]
                        + potential[i][j - jump] + signalDensity(x, y) * Math.pow(this.jump * deltaX, 2.0)) / 4.0;
    }

    private int getIndexX(double x) {
        return (int) ((x + this.bindingBox.getRangeX().getEnd()) / this.deltaX);
    }

    private int getIndexY(double y) {
        return (int) ((y + this.bindingBox.getRangeY().getEnd()) / this.deltaY);
    }

    private double differentialForX(double x, double y) {
        int i = getIndexX(x);
        int j = getIndexY(y);
        return (this.potential[i + jump][j] - this.potential[i][j]) / (jump * deltaX);
    }

    private double differentialForY(double x, double y) {
        int i = getIndexX(x);
        int j = getIndexY(y);
        return (this.potential[i][j + jump] - this.potential[i][j]) / (jump * deltaY);
    }

    public double[][] getPotential() {
        return potential;
    }

    public Double getDeltaX() {
        return deltaX;
    }

    public Double getDeltaY() {
        return deltaY;
    }

    public Box getBindingBox() {
        return bindingBox;
    }

    public static class Box {
        private Range rangeX;
        private Range rangeY;

        public Box(Range rangeX, Range rangeY) {
            this.rangeX = rangeX;
            this.rangeY = rangeY;
        }

        public Range getRangeX() {
            return this.rangeX;
        }

        public Range getRangeY() {
            return this.rangeY;
        }
    }

    public static class Range {
        private Double start;
        private Double end;

        public Range(Double start, Double end) {
            this.start = start;
            this.end = end;
        }

        public Double getStart() {
            return this.start;
        }

        public Double getEnd() {
            return this.end;
        }
    }
}
