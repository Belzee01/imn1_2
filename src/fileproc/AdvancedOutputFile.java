package fileproc;

import poisson.WariantA;

import java.util.ArrayList;
import java.util.List;

public class AdvancedOutputFile<T> {

    private List<Section<Double>> sections;

    private String filename;

    public AdvancedOutputFile(WariantA wariantA, String filename) {
        this.sections = new ArrayList<>();

        for (double i = wariantA.getBindingBox().getRangeX().getStart(); i <= wariantA.getBindingBox().getRangeX().getEnd(); i += wariantA.getDeltaX()) {
            Section<Double> section = new Section<>();
            for (double j = wariantA.getBindingBox().getRangeY().getStart(); j <= wariantA.getBindingBox().getRangeY().getEnd(); j += wariantA.getDeltaY()) {
                int indexX = (int) ((i + wariantA.getBindingBox().getRangeX().getEnd())/ wariantA.getDeltaX());
                int indexY = (int) ((j + wariantA.getBindingBox().getRangeY().getEnd())/ wariantA.getDeltaY());

                section.addRecord(new Record<>(i, j, wariantA.getPotential()[indexX][indexY]));
            }
            sections.add(section);
        }

        this.filename = filename;
    }

    public AdvancedOutputFile(double[][] potential, WariantA.Box box, double delta, String filename) {
        this.sections = new ArrayList<>();

        for (double i = box.getRangeX().getStart(); i <= box.getRangeX().getEnd(); i += delta) {
            Section<Double> section = new Section<>();
            for (double j = box.getRangeY().getStart(); j <= box.getRangeY().getEnd(); j += delta) {
                int indexX = (int) ((i + box.getRangeX().getEnd())/delta);
                int indexY = (int) ((j + box.getRangeY().getEnd())/delta);

                section.addRecord(new Record<>(i, j, potential[indexX][indexY]));
            }
            sections.add(section);
        }

        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        this.sections.forEach(s -> sb.append(s.toString()));

        return sb.toString();
    }

    private class Record<R> {
        private R x;
        private R y;
        private R z;

        public Record(R x, R y, R z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return String.valueOf(x) + "\t" + String.valueOf(y) + "\t" + String.valueOf(z) + "\n";
        }
    }

    private class Section<S> {
        private List<Record<S>> records;

        public Section() {
            this.records = new ArrayList<>();
        }

        public List<Record<S>> getRecords() {
            return records;
        }

        public void addRecord(Record<S> record) {
            this.records.add(record);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            this.records.forEach(r -> sb.append(r.toString()));
            sb.append("\n");

            return sb.toString();
        }
    }
}
