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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Graphics;
/**
 *
 * @author henryhammond
 */
public class Triangle extends DrawableGameObject implements VectorDrawable, Actor {

    public ArrayList<GameObject> bullets;
    private double bulletSpacing = .00001;
    private long lastBullet = 0;
    private double bulletSpeed = 0.5;
    public Triangle() {
        super();
        double[][] shape = {
            {0, 0}, {50, 100}, {100, 0},};
        setInertiaBasedMovement(true);
        setPoly(shape);
        setShift(0, 0);
        setTransformPoint(50, 50);
        setScale(0.08);
        setRotation(0);
        setColor(new Color(255, 255, 255));
        setShaded(true);
        addKeyListener();
        setVelocity(0,0);

        bullets = new ArrayList();
    }

    @Override
    public void act() {
        super.act();
            // bullets.add(new bullet(generateDrawPoly()[1][0], generateDrawPoly()[1][1],0.5, getRotation()+Math.PI/2));

        for(int i=0;i<bullets.size();i++){
            bullets.get(i).act();
        }
    }
    
    @Override
    public void move(double distance, double angle){
        super.move(distance/10,angle);
        
    }

    @Override
    public void keyNotification(ArrayList keys) {

        double acc = 0.05;

        if (keys.contains(KeyEvent.VK_UP)) {
            move(acc, getRotation() + Math.PI / 2);
        }
        if (keys.contains(KeyEvent.VK_DOWN)) {
            move(-acc, getRotation() + Math.PI / 2);
        }
        if (keys.contains(KeyEvent.VK_LEFT)) {
            rotate(-Math.PI / 100);
        }
        if (keys.contains(KeyEvent.VK_RIGHT)) {
            rotate(Math.PI / 100);
        }
        if( keys.contains(KeyEvent.VK_SPACE)) {
            //while( Math.random()*10 <= 9.999){
                shoot();
            //}
        }
    }

    public void shoot(){
        if( System.currentTimeMillis() - lastBullet >= bulletSpacing){
            double speed = bulletSpeed;
            double scope = 0.25;
            double[] n = EMacro.polarToVector( getRotation()+Math.PI/2 + Math.random()*Math.PI/scope - Math.PI/scope/2,speed);
            double[] vel = EMicro.add( getVelocity() , n);

            bullets.add(new bullet(generateDrawPoly()[1][0], generateDrawPoly()[1][1],vel, getRotation()+Math.PI/2));
            lastBullet = System.currentTimeMillis();
        }
    }

    public double getX() {
        return getShift()[0]+getTransformPoint()[0];
    }

    public double getY() {
        return getShift()[1]+getTransformPoint()[1];
    }

    public void setX(double x){
        setShift(x,getY());
    }
    public void setY(double y){
        setShift(getX(),y);
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);

        for(int i=0;i<bullets.size();i++){
            if( ( (bullet)bullets.get(i)).alive() == false ){
                bullets.remove(i);
            }
            else{
                bullets.get(i).draw(g);
            }
        }
    }
    public boolean inFrame(double x, double y, double w, double h){
        if( bullets.size() > 0){
            return true;
        }
        else{
            return super.inFrame(x,y,w,h);
        }
    }
}

class bullet extends DrawableGameObject implements VectorDrawable, Actor{

    private long maxLife;
    private long last;
    private long life;
    public bullet(double x, double y, double[] speed, double direction){
        super();
        double [][] shape = {
            {0,-5},{5,0},{0,5},{-5,0}
        };
        setInertiaBasedMovement(true);
        setPoly(shape);
        setShift(x, y);
        setTransformPoint(0, 0);
        setScale(0.3);
        setRotation(direction);
        setColor(new Color(255, 255, 255,25));
        setShaded(false);

        //double [] v = EMacro.polarToVector(direction,speed);

        this.setVelocity(speed);
        last = System.currentTimeMillis();
        life = 5000;
        life+= (int)Math.random()*1000-500;
        maxLife = life;
    }

    @Override
    public void act(){
        super.act();
        life -= System.currentTimeMillis()-last;
        last = System.currentTimeMillis();

        //int col = 20;
        int col = Math.min(255, Math.max(0,(int) (255*( (float)life/(float)maxLife ))));
        //System.out.println( col );
        setColor(new Color(255,255,255, col));
    }

    public boolean alive(){
        return life >= 0;

    }

}