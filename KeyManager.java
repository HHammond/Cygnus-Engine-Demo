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

import java.util.ArrayList;

/**
 *
 * @author henryhammond
 */
public class KeyManager {

    private ArrayList<Integer> keys;
    private ArrayList<Integer> listeningElements;
    private ArrayList<GameElement> elements;

    public KeyManager(ArrayList<GameElement> elements) {
        keys = new ArrayList();
        listeningElements = new ArrayList() {};
        this.elements = elements;
    }

    public void keyDown(int keyCode) {
        if (!isDown(keyCode)) {
            keys.add(keyCode);
        }
    }

    public void keyUp(int keyCode) {
        if (isDown(keyCode)) {
            keys.remove(keys.indexOf(keyCode));
        }
    }

    public boolean isDown(int keyCode) {
        if (keys.contains((Integer)keyCode)) {
            return true;
        } else {
            return false;
        }
    }

    public void setElements(ArrayList<GameElement> e) {
        this.elements = e;
    }

    public void setListeningElements(ArrayList<Integer> e) {
        this.listeningElements = e;
    }

    public void notifyListeningElements() {
        //System.out.println(keys);
        for (int i = 0; i < listeningElements.size(); i++) {
            if(elements.get(listeningElements.get(i)).listeningToKeys()){
                elements.get(listeningElements.get(i)).keyNotification(keys);
            }
        }
    }
}
