package flappygame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int x, y, width, height;
    protected Color bgColor = Color.GREEN;
    protected BufferedImage image = null;

    // default constructor to initialize a sprite (player / pipe)
    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // function to draw the sprite
    public void draw(Graphics g){
        if(image == null){
            g.setColor(bgColor);
            g.fillRect(x,y,width,height);
        }
        else {
            g.drawImage(image,x,y,width,height,null);
        }
    }

    // helper functions for getting the sprite bounds / X position
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }
}