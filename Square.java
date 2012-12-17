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

/**
 *
 * @author henryhammond
 */
public class Square extends DrawableGameObject{

    private double angle;
    private double rotAngle;
    public Square(){
        super();
        this.setShift(100,100);
        //this.setShift(Math.random()*800,Math.random()*600);
        double [][] p100 = {
            {0,0},{0,10},{10,10},{10,0}
        };
        this.setPoly(p100);
        this.setShift(400, 400);
        this.setTransformPoint(5, 5);
        this.setScale(1);
        this.setRotation(0);
        this.setColor(new Color(255, 0, 0));
        this.setShaded(true);
        this.addKeyListener();
        this.setInertiaBasedMovement(false);
        angle = (double)Math.random() * (double)Math.PI*2;
        rotAngle = Math.PI/100+Math.random()*Math.PI*2;
    }

    @Override
    public void act(){
        //rotAngle+=Math.PI/1000;
        rotate(rotAngle);
        this.move(Math.sin(angle),Math.cos(angle));
        //this.move(10, Math.random()*Math.PI*2);
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
    }

}
