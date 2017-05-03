package fileproc;

import poisson.Relaxation;

import java.util.ArrayList;
import java.util.List;

public class AdvancedOutputFile<T> {

    private List<Section<Double>> sections;

    private String filename;

    public AdvancedOutputFile(Relaxation relaxation, String filename) {
        this.sections = new ArrayList<>();

        for (double i = relaxation.getBindingBox().getRangeX().getStart(); i <= relaxation.getBindingBox().getRangeX().getEnd(); i += relaxation.getDeltaX()) {
            Section<Double> section = new Section<>();
            for (double j = relaxation.getBindingBox().getRangeY().getStart(); j <= relaxation.getBindingBox().getRangeY().getEnd(); j += relaxation.getDeltaY()) {
                int indexX = (int) ((i + relaxation.getBindingBox().getRangeX().getEnd())/relaxation.getDeltaX());
                int indexY = (int) ((j + relaxation.getBindingBox().getRangeY().getEnd())/relaxation.getDeltaY());

                section.addRecord(new Record<>(i, j, relaxation.getPotential()[indexX][indexY]));
            }
            sections.add(section);
        }

        this.filename = filename;
    }

    public AdvancedOutputFile(double[][] potential, Relaxation.Box box, double delta, String filename) {
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
