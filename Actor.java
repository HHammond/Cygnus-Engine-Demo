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
 * The Actor interface specifies objects that will have a logical component to
 * their running.
 * 
 * @author henryhammond
 */
public interface Actor {

    /**
     * The Act() method is the logical side of the game loop. 
     * All logical actions should be placed inside of the Act() method
     * 
     */
    public void act();
}
