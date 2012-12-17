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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * The DispalyWindow class is the class that runs the game loop, draws graphics
 * and holds the data objects used in the gameengine loop.
 * 
 * @author henryhammond
 */
public class DisplayWindow extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private BufferedImage backbuffer;
    private Graphics bg;
    private Graphics2D g2d;
    private Thread gfx;
    private Dimension size;
    private double FPS = 120;
    private KeyManager keyboardManager;
    private ArrayList<GameElement> GameElementList;
    private ArrayList<GameElement> drawableElements;
    private ArrayList<Integer> keylistenerElements;
    private ArrayList<Integer> mouselistenerElements;

    private int currentFrameRate = 0;

    Triangle t;
    
    /**
     * The constructor
     */
    public DisplayWindow() {

        super();

        size = new Dimension(800, 600);
        setSize(size);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0));

        //initiate keyboard manager
        keyboardManager = new KeyManager(GameElementList);

        // Generate GFX
        backbuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if ((backbuffer.getWidth() != this.getSize().width) || (backbuffer.getHeight() != this.getSize().height)) {
            // reinitialize the drawing area
            backbuffer = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB);
            backbuffer.setAccelerationPriority(TOP_ALIGNMENT);
            g2d = backbuffer.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        }

        //Add game objects
        GameElementList = new ArrayList();
        
        for(int i = 0; i < 50;i++){
            GameElementList.add(new Square());
        }

        for(int i = 0; i < 1; i++){
            t = new Triangle();
            GameElementList.add(t);
        }

        //Start thread
        gfx = new Thread(this);
        gfx.start();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

    }
    
    /**
     * Draw graphics to the screen
     * @param g the graphic context
     * @throws NullPointerException 
     */
    @Override
    public void paint(Graphics g) throws NullPointerException {
        long t1 = System.nanoTime();
        try {
            //Ensure that graphics display is set up properly
            size = this.getSize();

            if ((backbuffer.getWidth() != this.getSize().width) || (backbuffer.getHeight() != this.getSize().height)) {
                // reinitialize the drawing area
                backbuffer = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB);
                g2d = backbuffer.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            // Draw background
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0, 0, this.getSize().width, this.getSize().height - 23);


            // Draw objects to buffer
            for (int i = 0; i < GameElementList.size(); i++) {
                //ensure game objects are drawable
                if (GameElementList.get(i).isDrawable()) {
                    //check if game element is on screen, if not, don't draw
                    //note: game rendering can be optimized if drawables override the inFrame() method as necessary
                    //      this probably only needs to happen if there's lots of instances of an object...
                    if(GameElementList.get(i).inFrame(0,0,this.getSize().width,this.getSize().height)){
                        GameElementList.get(i).draw(g2d);                        
                    }
                }
            }

            //draw buffer to the screen
            g.drawImage(backbuffer, 0, 23, this);
            g.setColor(new Color(0,0,255));
            //g.drawString("FPS:"+currentFrameRate,50,50);
            g.drawString("Objects" + (GameElementList.size()+t.bullets.size()),200,50);
        } catch (Exception e) {
        }
        long t2 = System.nanoTime();
        double d = (t2-t1);
        g.drawString("RenderTime: "+d*Math.pow(10,-9),50,80);
        g.drawString("Real FPS  : "+1*Math.pow(10,9)/(d),50,100);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void run() {

        /*
         * By measuring the amount of time it takes to execute all necessary
         * commands, the time of each delay in the loop can be adjusted to
         * accurately calculate the FPS of the game, optimize FPS to the ideal
         * value and reduce the effects of lag on the system by a few milliseconds.
         * These few milliseconds compound rather quickly and allow for more
         * computations per second, more details and a better experience to the
         * end user.
         */

        assert FPS != 0;

        long lagRecovery = 0;
        long sleepTime = (long) (1000 / FPS);
        final long idealSleepTime = (long) (1000 / FPS);

        long baseTime;
        long postTime;
        long deltaTime;

        //make sure that all objects that are listening to key and mouse events
        //are in the proper listening lists
        int counter = 0;
        
        final int listenerCheckFrequency = (int)FPS;

        while (true) {
            //initialize loop timer
            baseTime = System.currentTimeMillis();

            if (counter++ >= listenerCheckFrequency) {
                keylistenerElements = new ArrayList();
                for (int i = 0; i < GameElementList.size(); i++) {
                    GameElement e = GameElementList.get(i);
                    if (e.listeningToKeys() == true) {
                        keylistenerElements.add(i);
                    }
                }
                keyboardManager.setElements(GameElementList);
                keyboardManager.setListeningElements(keylistenerElements);
                counter = 0;
            }

            //Process mouse movements
            

            //Process Keys
            keyboardManager.notifyListeningElements();

            //Run game object scripts
            for (int i = 0; i < GameElementList.size(); i++) {
                GameElementList.get(i).act();
            }

            //update graphics
            repaint();

            //Pause the System loop between frames
            postTime = System.currentTimeMillis();
            deltaTime = postTime - baseTime;

            sleepTime = idealSleepTime - deltaTime;

            try{
                currentFrameRate = (int) (1000 / deltaTime);
            }
            catch(ArithmeticException e){
                currentFrameRate = 1000;
            }
            if (sleepTime > 0) {
                //The system is not lagging behind ideal times
                try {
                    Thread.sleep(sleepTime-lagRecovery);
                } catch (Exception e) {
                }

            } else {
                //The system is lagging...
                //TODO: code to reduce system lag
                lagRecovery = sleepTime;
                lagRecovery%= idealSleepTime;
            }

        }
    }

    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseDragged(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyTyped(KeyEvent ke) {
        keyPressed(ke);
    }

    public void keyPressed(KeyEvent ke) {
        keyboardManager.keyDown(ke.getKeyCode());
    }

    public void keyReleased(KeyEvent ke) {
        keyboardManager.keyUp(ke.getKeyCode());
    }
}
