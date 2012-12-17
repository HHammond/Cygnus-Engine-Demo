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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author henryhammond
 */
public class DrawableGameObject extends GameObject implements VectorDrawable {

    //object variables
    private double[][] poly;
    private double[][] drawPoly;
    private double[] shift;
    private double[] transformPoint;
    private double scale;
    private double rotation;
    private Color color;
    private boolean shading;
    private boolean inertiaMovement;
    private double[] velocity;

    //These variables are used to keep track of previous 
    //states to save on computation time when possible instead
    //of generating a new polygon each frame when no movement has occured
    private double lastScale = 0;
    private double lastRotation = 0;
    private double[] lastShift = {0, 0};
    private double[][] lastPoly = null;

    public DrawableGameObject() {
        super();
        this.isDrawable = true;
        this.color = (new Color(255, 255, 255));
        shift = new double[2];
        poly = new double[0][2];
        transformPoint = new double[2];
        inertiaMovement = false;
        velocity = new double[2];
    }

    @Override
    public void act() {
        super.act();
        if (inertiaBasedMovement()) {
            this.setShift(EMicro.add(this.getShift(), getVelocity()));
        }
    }

    public double[][] generateDrawPoly() {
        double[][] drawShape = this.getPoly();

        drawShape = EMacro.unshiftPoly(drawShape, getTransformPoint());

        if (getScale() != 1) {
            drawShape = EMacro.scalePoly(drawShape, getScale());
        }
        if (getRotation() != 0) {
            drawShape = EMacro.rotatePoly(drawShape, getRotation());
        }
        final double[] zero = {0, 0};
        if (getShift() != zero) {
            drawShape = EMacro.shiftPoly(drawShape, getShift());
        }
        drawShape = EMacro.shiftPoly(drawShape, getTransformPoint());

        return drawShape;
    }

    @Override
    public void draw(Graphics g) {

        double[][] drawShape = null;

        if (getScale() == lastScale && getRotation() == lastRotation && getShift() == lastShift && lastPoly != null) {
            drawShape = lastPoly;
        } else {

            drawShape = generateDrawPoly();
        }

        g.setColor(this.getColor());

        Polygon s = new Polygon();
        for (int i = 0; i < drawShape.length; i++) {
            s.addPoint((int) Math.round(drawShape[i][0]), (int) Math.round(drawShape[i][1]));
        }

        if (isShaded()) {
            g.fillPolygon(s);
        } else {
            g.drawPolygon(s);
        }

        lastScale = getScale();
        lastRotation = getRotation();
        lastShift = getShift();
        lastPoly = drawShape;

    }

    public boolean inFrame(double x, double y, double w, double h) {
        final double[][] drawShape = generateDrawPoly();
        for (int i = 0; i < drawShape.length; i++) {

            if (inRect((int) Math.round(drawShape[i][0]), (int) Math.round(drawShape[i][1]), x, y, w, h)) {
                return true;
            }
        }
        return false;
    }

    private boolean inRect(int px, int py, int rx, int ry, int width, int height) {
        return (px >= rx && px <= rx + width && py >= ry && py <= ry + height);
    }

    private boolean inRect(double px, double py, double rx, double ry, double width, double height) {
        return (px >= rx && px <= rx + width && py >= ry && py <= ry + height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setShaded(boolean b) {
        this.setShading(true);
    }

    public boolean isShaded() {
        return this.isShading();
    }

    public double[][] getPoly() {
        return this.poly.clone();
    }

    /**
     * @return the shift
     */
    public double[] getShift() {
        return shift;
    }

    public void setShift(double x, double y) {
        this.getShift()[0] = x;
        this.getShift()[1] = y;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * @return the rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void rotate(double angle) {
        this.setRotation(this.getRotation() + angle);
    }

    /**
     * @param shading the shading to set
     */
    public void setShading(boolean shading) {
        this.shading = shading;
    }

    /**
     * @return the transformPoint
     */
    public double[] getTransformPoint() {
        return transformPoint;
    }

    public void setTransformPoint(double x, double y) {
        getTransformPoint()[0] = x;
        getTransformPoint()[1] = y;
    }

    public void move(double distance, double angle) {
        if (inertiaBasedMovement()) {
            setVelocity(EMicro.add(getVelocity(), EMacro.polarToVector(angle, distance)));
        } else {
            this.setShift(EMicro.add(this.getShift(), EMacro.moveByAngle(angle, distance)));
        }
    }

    /**
     * @param poly the poly to set
     */
    public void setPoly(double[][] poly) {
        this.poly = poly;
    }

    /**
     * @return the drawPoly
     */
    public double[][] getDrawPoly() {
        return drawPoly;
    }

    /**
     * @param drawPoly the drawPoly to set
     */
    public void setDrawPoly(double[][] drawPoly) {
        this.drawPoly = drawPoly;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(double[] shift) {
        this.shift = shift;
    }

    /**
     * @param transformPoint the transformPoint to set
     */
    public void setTransformPoint(double[] transformPoint) {
        this.transformPoint = transformPoint;
    }

    /**
     * @return the shading
     */
    public boolean isShading() {
        return shading;
    }

    /**
     * @return the inertiaMovement
     */
    public boolean inertiaBasedMovement() {
        return inertiaMovement;
    }

    /**
     * @param inertiaMovement the inertiaMovement to set
     */
    public void setInertiaBasedMovement(boolean inertiaBasedMovement) {
        this.inertiaMovement = inertiaBasedMovement;
    }

    /**
     * @return the velocity
     */
    public double[] getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double x, double y) {
        double[] v = {x, y};
        setVelocity(v);
    }
}
