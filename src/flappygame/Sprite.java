package flappygame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int x, y, width, height;
    protected int dy;
    protected Color bgColor = Color.GREEN;
    protected BufferedImage image = null;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(){
        dy += 1;
        y += dy;

        if (y > GameBoard.BOARD_HEIGHT - height) {
            y = GameBoard.BOARD_HEIGHT - height;
            dy = 0;
        }
    }

    public void jump(){
        dy = -10;
    }

    public void draw(Graphics g){
        if(image == null){
            g.setColor(bgColor);
            g.fillRect(x,y,width,height);
        }
        else {
            g.drawImage(image,x,y,width,height,null);
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }

}
