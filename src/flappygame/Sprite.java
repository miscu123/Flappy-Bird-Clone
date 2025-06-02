package flappygame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    protected int x, y, width, height;
    protected int dx, dy;
    protected Color bgColor = Color.GREEN;
    protected BufferedImage image = null;
    protected boolean visible = true;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(int x, int y, int width, int height, boolean visible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    public void move(){
        dy += 1; // gravitație constantă
        y += dy;

        // limită pentru a nu trece de jos
        if (y > GameBoard.BOARD_HEIGHT - height) {
            y = GameBoard.BOARD_HEIGHT - height;
            dy = 0;
        }
    }

    public void jump(){
        dy = -10;
    }

    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
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

    public void loadImage(String fileName){
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
