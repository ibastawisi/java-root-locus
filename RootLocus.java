import org.ejml.data.Complex_F64;

public class RootLocus {

    private double[] numerator;
    private double[] denominator;
    private Complex_F64[][] roots;

    public RootLocus(double[] numerator, double[] denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.roots = calculatRoots();
    }

    private Complex_F64[][] calculatRoots() {
        int minK = 0;
        int maxK = 1000;
        double step = 0.01;
        int coeffLength = Math.max(numerator.length, denominator.length);
        Complex_F64[][] roots = new Complex_F64[(int) ((maxK - minK) / step)][denominator.length];
        for (double k = minK; k < maxK; k += step) {
            double[] coefficients = new double[coeffLength];
            for (int i = 0; i < denominator.length; i++) {
                coefficients[i] = denominator[i];
            }
            for (int i = 0; i < numerator.length; i++) {
                coefficients[i] += numerator[i] * k;
            }
            roots[(int) ((k - minK) / step)] = PolynomialRootFinder.findRoots(coefficients);
        }
        return roots;
    }

    public Complex_F64[][] getRoots() {
        return roots;
    }
}