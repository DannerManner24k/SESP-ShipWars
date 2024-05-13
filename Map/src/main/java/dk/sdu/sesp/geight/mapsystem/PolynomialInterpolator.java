package dk.sdu.sesp.geight.mapsystem;


public class PolynomialInterpolator {

    public static double[] interpolate(double[][] points) {
        int n = points.length;  // Number of points
        double[][] vandermonde = new double[n][n];
        double[] y = new double[n];

        // Fill the Vandermonde matrix and y-vector
        for (int i = 0; i < n; i++) {
            double x = points[i][0];
            y[i] = points[i][1];
            double power = 1;
            for (int j = 0; j < n; j++) {
                vandermonde[i][n - j - 1] = power;  // Fill from right to left
                power *= x;
            }
        }

        // Solve the Vandermonde matrix for coefficients using Gaussian Elimination
        return gaussianElimination(vandermonde, y);
    }

    private static double[] gaussianElimination(double[][] matrix, double[] vector) {
        int n = vector.length;
        for (int i = 0; i < n; i++) {
            // Search for maximum in this column
            double maxEl = Math.abs(matrix[i][i]);
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix[k][i]) > maxEl) {
                    maxEl = Math.abs(matrix[k][i]);
                    maxRow = k;
                }
            }

            // Swap maximum row with current row (column by column)
            for (int k = i; k < n; k++) {
                double tmp = matrix[maxRow][k];
                matrix[maxRow][k] = matrix[i][k];
                matrix[i][k] = tmp;
            }
            double temp = vector[maxRow];
            vector[maxRow] = vector[i];
            vector[i] = temp;

            // Make all rows below this one 0 in current column
            for (int k = i + 1; k < n; k++) {
                double c = -matrix[k][i] / matrix[i][i];
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        matrix[k][j] = 0;
                    } else {
                        matrix[k][j] += c * matrix[i][j];
                    }
                }
                vector[k] += c * vector[i];
            }
        }

        // Solve equation Ax=b for an upper triangular matrix A
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = vector[i] / matrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                vector[k] -= matrix[k][i] * solution[i];
            }
        }
        return solution;
    }
}
