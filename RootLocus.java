import org.ejml.data.Complex_F64;

public class RootLocus {

    private double[] numerator;
    private double[] denominator;
    private double step;
    private Complex_F64[] zeros;
    private Complex_F64[] poles;

    public RootLocus(double[] numerator, double[] denominator, double step) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.step = step;
        this.zeros = calculateZeros(numerator);
        this.poles = calculatePoles(denominator);
    }

    public double getStep() {
        return step;
    }

    private Complex_F64[][] calculatRoots() {
        int minK = 0;
        double maxK = step * 25000;
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

    private Complex_F64[] calculatePoles(double[] denom) {
        Complex_F64[] poles = PolynomialRootFinder.findRoots(denom);
        return poles;
    }

    private Complex_F64[] calculateZeros(double[] num) {
        Complex_F64[] zeros = PolynomialRootFinder.findRoots(num);
        return zeros;
    }

    private Complex_F64 calculateAsymptotesCentroid() {
        Complex_F64 centroid = new Complex_F64(0, 0);
        for (int i = 0; i < poles.length; i++) {
            centroid.real += poles[i].real;
            centroid.imaginary += poles[i].imaginary;
        }
        for (int i = 0; i < zeros.length; i++) {
            centroid.real -= zeros[i].real;
            centroid.imaginary -= zeros[i].imaginary;
        }
        centroid.real /= poles.length - zeros.length;
        centroid.imaginary /= poles.length - zeros.length;
        return centroid;
    }

    private double[] calculateAsymptotesAngles() {
        double[] angles = new double[poles.length - zeros.length];
        for (int i = 0; i < angles.length; i++) {
            angles[i] = (180 - 360 * i) / (poles.length - zeros.length);
        }
        return angles;
    }

    private double[] calculateDepartureAngles() {
        double[] departureAngles = new double[poles.length];
        for (int i = 0; i < poles.length; i++) {
            double polesAngles = 0;
            double zerosAngles = 0;
            for (int j = 0; j < poles.length; j++) {
                if (i != j) {
                    polesAngles += Math
                            .atan((poles[i].imaginary - poles[j].imaginary) / (poles[i].real - poles[j].real));
                }
            }
            for (int j = 0; j < zeros.length; j++) {
                zerosAngles += Math.atan((poles[i].imaginary - zeros[j].imaginary) / (poles[i].real - zeros[j].real));
            }
            departureAngles[i] = 180 - polesAngles * 180 / Math.PI + zerosAngles * 180 / Math.PI;

        }
        return departureAngles;
    }

    private double[] calculateArrivalAngles() {
        double[] arrivalAngles = new double[zeros.length];
        for (int i = 0; i < zeros.length; i++) {
            double zerosAngles = 0;
            double polesAngles = 0;
            for (int j = 0; j < zeros.length; j++) {
                if (i != j) {
                    zerosAngles += Math
                            .atan((zeros[i].imaginary - zeros[j].imaginary) / (zeros[i].real - zeros[j].real));
                }
            }
            for (int j = 0; j < poles.length; j++) {
                polesAngles += Math.atan((zeros[i].imaginary - poles[j].imaginary) / (zeros[i].real - poles[j].real));
            }
            arrivalAngles[i] = -180 - zerosAngles * 180 / Math.PI + polesAngles * 180 / Math.PI;
        }
        return arrivalAngles;

    }

    public Complex_F64[] getZeros() {
        return zeros;
    }

    public Complex_F64[] getPoles() {
        return poles;
    }

    public Complex_F64 getAsymptotesCentroid() {
        return calculateAsymptotesCentroid();
    }

    public double[] getAsymptotesAngles() {
        return calculateAsymptotesAngles();
    }

    public double[] getDepartureAngles() {
        return calculateDepartureAngles();
    }

    public double[] getArrivalAngles() {
        return calculateArrivalAngles();
    }

    public Complex_F64[][] getRoots() {
        return calculatRoots();
    }
}