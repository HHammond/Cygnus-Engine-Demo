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
public interface GameElement {

    public void act();

    public boolean isDrawable();

    public void addKeyListener();

    public void addMouseListener();

    public void removeKeyListener();

    public void removeMouseListener();

    public boolean listeningToKeys();

    public boolean listeningToMouse();

    public boolean inFrame(double x, double y, double w, double h);

    public void keyNotification(ArrayList<Integer> keysDown);

    public void mouseNotification();

    public void draw(Graphics g);
}
