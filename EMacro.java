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
 * High level transformations for Polygons and points using the EMicro's
 * logic
 *
 * This is mostly where the magic and math happens
 *
 * @author henryhammond
 */
public class EMacro {

    public static double[] shiftPoint(double[] originalPoint, double[] amount) {
        double[] n = {originalPoint[0] + amount[0], originalPoint[1] + amount[1]};

        return n;
    }

    public static double[] unshiftPoint(double[] originalPoint, double[] amount) {
        double[] n = {originalPoint[0] - amount[0], originalPoint[1] - amount[1]};

        return n;
    }

    public static double[] rotatePoint(double[] point, double angle) {
        double a = EMicro.angle(point);
        double[][] r = EMicro.rotation(angle);

        return EMicro.multiply(point, r);
    }

    public static double[] scalePoint(double[] point, double magnitude) {
        return EMicro.multiply(point, EMicro.scale(magnitude));
    }

    public static double[] stretchPoint(double[] point, double x, double y) {
        return EMicro.multiply(point, EMicro.stretch(x, y));
    }

    public static double[][] rotatePoly(double[][] poly, double angle) {
        double[][] r = EMicro.rotation(angle);
        double[][] n = poly;

        for (int i = 0; i < n.length; i++) {
            n[i] = EMicro.multiply(n[i], r);
        }

        return n;
    }

    public static double[][] scalePoly(double[][] poly, double amount) {
        return stretchPoly(poly, amount, amount);
    }

    public static double[][] stretchPoly(double[][] poly, double x, double y) {
        double[][] n = poly;

        for (int i = 0; i < n.length; i++) {
            n[i] = stretchPoint(n[i], x, y);
        }

        return n;
    }

    public static double[][] shiftPoly(double[][] poly, double[] amount) {
        double[][] n = poly;

        for (int i = 0; i < n.length; i++) {
            n[i] = shiftPoint(n[i], amount);
        }

        return n;
    }

    public static double[][] unshiftPoly(double[][] poly, double[] amount) {
        double[][] n = poly;

        for (int i = 0; i < n.length; i++) {
            n[i] = unshiftPoint(n[i], amount);
        }

        return n;
    }

    public static double angleFromP2P(double[] p1, double[] p2) {
        double[] n = EMicro.subtract(p1, p2);

        return EMicro.angle(n);
    }

    public static double[] moveByAngle(double angle, double distance) {
        double[] n = new double[2];
        n[0] = distance * Math.cos(angle);
        n[1] = distance * Math.sin(angle);

        return n;
    }

    public static double [] polarToVector(double angle,double distance){
        return moveByAngle(angle,distance);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

