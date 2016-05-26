package digitrecognizer;

import java.util.Arrays;

/**
 * Specific matrix - a column vector. It doesn't extend Matrix class due to
 * efficiency reason.
 */
class ColumnVector {
    protected double[] data;
    protected int height;

    public ColumnVector(double[] data) {
        this.data = data;
        height = data.length;
    }

    /** Applies unary function f to all vector elements. */
    public void applyFunction(Function f) {
        for (int i = 0; i < height; i++) {
            data[i] = f.apply((data[i]));
        }
    }

    /** Adds 1 as a first element to the vector. */
    public void extendByOne() {
        height += 1;
        double[] new_data = new double[height];
        System.arraycopy(data, 0, new_data, 1, height - 1);
        new_data[0] = 1;
        data = new_data;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0 ; i < height; i++) {
            str.append(String.valueOf(data[i])).append("\t");
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColumnVector)) {
            return false;
        } else {
            ColumnVector v = (ColumnVector) o;
            return Arrays.equals(data, v.data);
        }
    }
}
