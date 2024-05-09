import org.ejml.data.Complex_F64;

public class RootLocus {

    private double[] numerator;
    private double[] denominator;
    private double step;
    private Complex_F64[][] roots;

    public RootLocus(double[] numerator, double[] denominator, double step) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.step = step;
        this.roots = calculatRoots();
    }

    public double getStep() {
        return step;
    }

    private Complex_F64[][] calculatRoots() {
        int minK = 0;
        double maxK = step * 10000;
        int coeffLength = Math.max(numerator.length, denominator.length);
        Complex_F64[][] roots = new Complex_F64[(int) ((maxK - minK) / step)][coeffLength];
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