package flappygame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int x, y, width, height;
    protected Color bgColor = Color.GREEN;
    protected BufferedImage image = null;

    /**
     * Default constructor to initialize a sprite (player / pipe)
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     */
    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Function to draw the sprite
     * @param g the Graphics object to draw with
     */
    public void draw(Graphics g){
        if(image == null){
            g.setColor(bgColor);
            g.fillRect(x,y,width,height);
        }
        else {
            g.drawImage(image,x,y,width,height,null);
        }
    }

    /** Helper functions for getting the sprite bounds / X position */

    /**
     * Gets the bounding rectangle of the sprite
     * @return Rectangle representing the sprite's bounds
     */
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    /**
     * Gets the x position of the sprite
     * @return the x coordinate of the sprite
     */
    public int getX() {
        return x;
    }
}