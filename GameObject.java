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

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author henryhammond
 */
public class GameObject implements GameElement {

    protected boolean isDrawable;
    protected boolean isListeningTokeys;

    public GameObject() {
        isDrawable = false;
        isListeningTokeys = false;
    }

    public void act() {
        //this code doesn't do anything yet
    }

    public boolean isDrawable() {
        return isDrawable;
    }

    public void draw(Graphics g) {
        //Not a drawable object... this method should NEVER be called
        //but is implemented for safety
    }

    public boolean listeningToKeys() {
        return isListeningTokeys;
    }

    //TODO: implement mouse listener code
    public boolean listeningToMouse() {
        return false;
    }

    public void mouseNotification() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyNotification(ArrayList<Integer> keys) {
        //This object's key events go here
        //by default objects don't accept any key events
    }

    public void addKeyListener() {
        this.isListeningTokeys = true;
    }

    public void addMouseListener() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeKeyListener() {
        this.isListeningTokeys = false;
    }

    public void removeMouseListener() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public boolean inFrame(double x, double y, double w, double h){
        if( !isDrawable()){
            return false;
        }
        return true;
    }
}
