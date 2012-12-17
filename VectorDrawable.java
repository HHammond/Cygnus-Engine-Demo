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
public interface VectorDrawable extends GameElement {

    public void setColor(Color c);

    public Color getColor();

    public void setShaded(boolean b);

    public boolean isShaded();

    public void setPoly(double[][] poly);

    public double[][] getPoly();

    public void draw(Graphics g);
}
