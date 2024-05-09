import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;

public class PolynomialRootFinder {
    /**
     * <p>
     * Given a set of polynomial coefficients, compute the roots of the polynomial.
     * Depending on
     * the polynomial being considered the roots may contain complex number. When
     * complex numbers are
     * present they will come in pairs of complex conjugates.
     * </p>
     *
     * <p>
     * Coefficients are ordered from least to most significant, e.g: y = c[0] +
     * x*c[1] + x*x*c[2].
     * </p>
     *
     * @param coefficients Coefficients of the polynomial.
     * @return The roots of the polynomial
     */
    public static Complex_F64[] findRoots(double... coefficients) {
        int N = coefficients.length - 1;

        // Construct the companion matrix
        DMatrixRMaj c = new DMatrixRMaj(N, N);

        double a = coefficients[N];
        for (int i = 0; i < N; i++) {
            c.set(i, N - 1, -coefficients[i] / a);
        }
        for (int i = 1; i < N; i++) {
            c.set(i, i - 1, 1);
        }

        // use generalized eigenvalue decomposition to find the roots
        EigenDecomposition_F64<DMatrixRMaj> evd = DecompositionFactory_DDRM.eig(N, false);

        evd.decompose(c);

        Complex_F64[] roots = new Complex_F64[N];

        for (int i = 0; i < N; i++) {
            roots[i] = evd.getEigenvalue(i);
        }

        return roots;
    }
}