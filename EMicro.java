/*//////////////////////////////////////////////////////////////////////////////

    Copyright (C) 2011  Henry Hammond
    email: HenryHHammond92@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or  any later
    version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    For a copy of the GNU Lesser General Public License, see
    <http://www.gnu.org/licenses/>.

//////////////////////////////////////////////////////////////////////////////*/

//package gameengine;

/**
 * Geometry engine by Henry Hammond
 * @author henryhammond
 */
public class EMicro {

    /**
     * Value approximating PI
     */
    public static double PI = Math.PI;

    private static void trace(String str) {
        System.out.println(str);
    }

    private static void trace(double in) {
        trace((double) (Math.round(in * 100)) / 100 + "");
    }

    private static void trace(double[] in) {
        for (int i = 0; i < in.length; i++) {
            System.out.print((double) (Math.round(in[i] * 100)) / 100 + " ");
        }

        System.out.print("\n");
    }

    private static void trace(double[][] in) {
        for (int i = 0; i < in.length; i++) {
            trace(in[i]);
        }

        trace("");
    }

    private static void trace(boolean in) {
        trace(in + "");
    }


    public static double projectOnX(double[] vector){
        return vector[0];
    }
    public static double projectOnX(double magnitude,double angle){
        return magnitude*Math.cos(angle);
    }
    public static double projectOnY(double magnitude,double angle){
        return magnitude*Math.sin(angle);
    }
    public static double projectOnLine(double[] vector, double[] line){
        double angle = angle(line);
        double magnitude = projectOnX( multiply(vector, rotation(-angle)) );
        return magnitude;
    }
    /**
     *
     * @param col column vector to be converted
     * @return converted column vector in form of {a...n}
     */
    public static double[] toRowVector(double[][] col) {
        assert col[0].length == 1;

        double[] n = new double[col.length];

        for (int i = 0; i < col.length; i++) {
            n[i] = col[i][0];
        }

        return n;
    }

    /**
     *
     * Convert row vector to a column vector
     *
     * @param row vector to be converted
     * @return column vector in from of {a},...,{n}
     */
    public static double[][] toColVector(double[] row) {
        double[][] n = new double[row.length][1];

        for (int i = 0; i < row.length; i++) {
            n[i][0] = row[i];
        }

        return n;
    }

    /**
     * Convert a row vector into a standardized form to allow easier calculations
     * @param row vecttor to be converted
     * @return formatted row vector
     */
    public static double[][] rowVectorToMatrix(double[] row) {
        return transpose(toColVector(row));
    }

    /**
     * Calculate dot product of two vectors
     * @param vectorA input vector 1
     * @param vectorB input vector 2
     * @return numerical calculation of dot product
     */
    public static double dotProduct(double[] vectorA, double[] vectorB) {
        return vectorA[0] * vectorB[0] + vectorA[1] * vectorB[1];
    }

    /**
     * Calculate angle between two vectors.
     * <br /><b>Note: Will only return values between 0 and PI</b>
     * @param vectorA
     * @param vectorB
     * @return angle between 0 and PI
     */
    public static double angleBetween(double[] vectorA, double[] vectorB) {
        double dot = dotProduct(vectorA, vectorB);

        if (dot == 0) {    // tan pi/2 = undefined...
            return PI / 2;
        }

        return Math.acos(dot / (magnitude(vectorA) * magnitude(vectorB)));
    }

    /**
     * Calculate angle between x axis and a vector or point
     * @param vector
     * @return angle between 0 and 2PI
     */
    public static double angle(double[] vector) {
        double[] baseline = {1, 0};
        double a = angleBetween(vector, baseline);

        if (vector[1] <= baseline[1]) {
            a *= -1;
        }

        return a;
    }

    /**
     * Length of a vector
     * @param vector
     * @return numeric value of a vector's length
     */
    public static double magnitude(double[] vector) {
        double v = 0;

        for (int i = 0; i < vector.length; i++) {
            v += vector[i] * vector[i];
        }

        return Math.sqrt(v);
    }

    /**
     * Multiply two row vectors in non formatted form.
     * <br />This is just a bit faster than using formatted form
     * @param v1
     * @param v2
     * @return
     */
    public static double[] multiply(double[] v1, double v2[]) {
        assert v1.length == v2.length;

        double[] n = new double[1];

        for (int i = 0; i < n.length; i++) {
            n[i] = 0;

            for (int j = 0; j < v1.length; j++) {
                n[i] += v1[j] * v2[j];
            }
        }

        return n;
    }

    /**
     * Multiply any two matrices.
     * @param m1
     * @param m2
     * @return
     */
    public static double[][] multiply(double[][] m1, double[][] m2) {
        if ((m1[0].length == m2.length) != true) {
            return null;
        }

        double[][] n = new double[m1.length][m2[0].length];

        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                n[r][c] = 0;
                for (int i = 0; i < m1[0].length; i++) {
                    n[r][c] += m1[r][i] * m2[i][c];
                }
            }
        }

        return n;
    }

    /**
     * Multiply vector by a matrix
     * @param v a vector
     * @param m a matrix
     * @return
     */
    public static double[] multiply(double[] v, double[][] m) {
        assert v.length == m[0].length;

        double[] n = new double[v.length];

        for (int i = 0; i < n.length; i++) {
            n[i] = 0;

            for (int j = 0; j < m.length; j++) {
                n[i] += v[j] * m[i][j];
            }
        }

        return n;
    }

    /**
     * Multiply a matrix by a scalar
     * @param m any matrix
     * @param x any scalar
     * @return
     */
    public static double[][] multiply(double[][] m, double x) {
        double[][] n = m;

        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                n[r][c] *= x;
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a scalar
     * @param m any vector
     * @param x any scalar
     */
    public static double[] multiply(double[] m, double x) {
        double[] n = new double[m.length];
        for (int i = 0; i < m.length; i++) {
            n[i] = m[i] * x;
        }

        return n;
    }

    /**
     * Generate a rotation matrix
     * @param angle of rotation
     * @return a new rotation matrix
     */
    public static double[][] rotation(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double[][] n = {
            {cos, -sin}, {sin, cos},};

        return n;
    }

    /**
     * Scale a matrix to a larger size
     * @param magnitude
     * @return a new scale matrix
     */
    public static double[][] scale(double magnitude) {
        return stretch(magnitude, magnitude);
    }

    public static double[][] stretch(double x, double y) {
        double[][] n = {
            {x, 0},
            {0, y}
        };
        return n;
    }

    /**
     * Shear matrix on x axis
     * @param shear
     * @return a new shear matrix
     */
    public static double[][] shearY(double shear) {
        double[][] n = {
            {1, 0}, {shear, 1},};

        return n;
    }

    /**
     * Shear matrix on y axis
     * @param shear
     * @return a new shear matrix
     */
    public static double[][] shearX(double shear) {
        double[][] n = {
            {1, shear}, {0, 1},};

        return n;
    }

    /**
     * flip a matrix in the x direction
     * @return new transform matrix
     */
    public static double[][] flipX() {
        double[][] n = {
            {-1, 0}, {0, 1},};

        return n;
    }

    /**
     * flip a matrix in the y direction
     * @return new transform matrix
     */
    public static double[][] flipY() {
        double[][] n = {
            {1, 0}, {0, -1},};

        return n;
    }

    /**
     * Find and return the determinant of a matrix
     * @param m matrix
     * @return determinant
     */
    public static double det(double[][] m) {
        //trace(m);
        assert m.length == m[0].length;

        if (m.length == 2) {
            double n = m[0][0] * m[1][1] - m[0][1] * m[1][0];
            return n;
        } else {
            double n = 0;
            double tmp = 0;

            for (int col = 0; col < m.length; col++) {
                tmp = 1;

                for (int h = 0; h < m.length; h++) {
                    tmp *= m[h][(col + h) % m.length];
                }

                n += tmp;
            }

            for (int col = 0; col < m.length; col++) {
                tmp = 1;

                for (int h = 0; h < m.length; h++) {
                    tmp *= m[h][(m.length + (col - h)) % m.length];
                }

                n -= tmp;
            }

            return n;
        }
    }

    public static double[][] invert(double[][] m) {
        assert isSquare(m);

        if (det(m) != 0) {

            double[][] n = multiply(adj(m), 1 / det(m));
            return n;
        }
        return null;
        //return n;
    }

    public static double[][] adj(double[][] m) {

        double[][] n = transpose(m);
        int[] coords = new int[2];
        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n.length; c++) {
                coords[0] = c;
                coords[1] = r;
                n[r][c] = det(minor(coords, m));
            }

        }

        int i = -1;
        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                i *= -1;
                n[r][c] *= i;
            }
        }
        return n;
    }

    public static double[][] minor(int[] coords, double[][] m) {
        double[][] minor = new double[2][2];
        int rows = 0;
        int cols = 0;

        for (int r = 0; r < m.length; r++) {
            if (r != coords[0]) {
                for (int c = 0; c < m[0].length; c++) {
                    if (c != coords[1]) {
                        minor[rows][cols] = m[r][c];
                        cols++;
                        if (cols >= 2) {
                            cols = 0;
                            rows++;
                        }
                    }
                }
            }
        }

        return minor;
    }

    /**
     * Reflect data in an axis
     * @param angle angle of line of reflection
     * @return
     */
    public static double[][] reflection(double angle) {

        double sin = Math.sin(2 * angle);
        double cos = Math.cos(2 * angle);

        double[][] n = {
            {cos, sin}, {sin, -cos}
        };

        return n;

    }

    /**
     * Add two matrices
     * @param a
     * @param b
     * @return Summative matrix of A and B
     */
    public static double[][] add(double[][] a, double[][] b) {
        assert (a.length == b.length) && (a[0].length == b[0].length);

        double[][] n = a;

        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                n[r][c] += b[r][c];
            }
        }

        return n;
    }

    /**
     * Add any two vectors or points
     * @param a
     * @param b
     * @return The new point
     */
    public static double[] add(double[] a, double[] b) {
        assert a.length == b.length;

        double[] n = a.clone();

        for (int i = 0; i < n.length; i++) {
            n[i] += b[i];
        }

        return n;
    }

    /**
     * Subtract vector a from vector b
     * @param a
     * @param b
     * @return new vector (a-b)
     */
    public static double[] subtract(double[] a, double[] b) {
        assert a.length == b.length;

        double[] n = a.clone();

        for (int i = 0; i < n.length; i++) {
            n[i] -= b[i];
        }

        return n;
    }

    /**
     * Subtract matrix a from matrix b
     * @param a
     * @param b
     * @return new matrix
     */
    public static double[][] subtract(double[][] a, double[][] b) {
        assert (a.length == b.length) && (a[0].length == b[0].length);

        double[][] n = a;

        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                n[r][c] -= b[r][c];
            }
        }

        return n;
    }

    /**
     * get width of a matrix
     * @param m matrix
     * @return
     */
    public static int width(double[][] m) {
        return m[0].length;
    }

    /**
     * get height of a matrix
     * @param m matrix
     * @return
     */
    public static int height(double[][] m) {
        return m.length;
    }

    /**
     * Check if a matrix is square or not.
     * @param m
     * @return
     */
    public static boolean isSquare(double[][] m) {
        return (m.length == m[0].length);
    }

    /**
     * Generate transpose of any row vector m
     * @param m
     * @return
     */
    public static double[][] transpose(double[] m) {
        return toColVector(m);
    }

    /**
     * Generate transpose matrix of any input matrix
     * @param m
     * @return
     */
    public static double[][] transpose(double[][] m) {
        double[][] n = new double[m[0].length][m.length];

        for (int r = 0; r < n.length; r++) {
            for (int c = 0; c < n[0].length; c++) {
                n[r][c] = m[c][r];
            }
        }

        return n;
    }

    /**
     * Check if two matrices are equal
     * @param m1
     * @param m2
     * @return
     */
    public static boolean isEqual(double[][] m1, double[][] m2) {
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length)) {
            return false;
        }

        for (int r = 0; r < m1.length; r++) {
            for (int c = 0; c < m1[0].length; c++) {
                if (m1[r][c] != m2[r][c]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check if any matrix is symmetrical
     * @param m
     * @return
     */
    public static boolean symmetric(double[][] m) {
        return isEqual(m, transpose(m));
    }

    static double distance(double[] a, double[] b) {

        double d = Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));

        return d;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

