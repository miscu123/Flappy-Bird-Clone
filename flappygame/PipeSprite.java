package flappygame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PipeSprite {
    private int x;
    private final int width;
    private final int topHeight;
    private final int bottomHeight;
    private final int gap;
    private final int speed;
    private boolean passed = false;
    private final BufferedImage topPipeImg;
    private final BufferedImage bottomPipeImg;

    // constructor for loading a pipe with certain specifications
    public PipeSprite(int x, int height, int gap, int speed, BufferedImage topPipeImg, BufferedImage bottomPipeImg) {
        this.x = x;
        this.width = 80;
        this.gap = gap;
        this.speed = speed;
        this.topHeight = (int)(Math.random() * (height - gap - 100)) + 50;
        this.bottomHeight = height - topHeight - gap;
        this.topPipeImg = topPipeImg;
        this.bottomPipeImg = bottomPipeImg;
    }

    // function to move the pipes to the left
    public void move() {
        x -= speed;
    }

    // function to draw the pipes
    public void draw(Graphics g) {
        if (topPipeImg != null && bottomPipeImg != null) {
            g.drawImage(topPipeImg, x, 0, width, topHeight, null);
            g.drawImage(bottomPipeImg, x, topHeight + gap, width, bottomHeight, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, 0, width, topHeight);
            g.fillRect(x, topHeight + gap, width, bottomHeight);
        }
    }

    // functions created to gather info about the pipes
    // created to ease our work with game logic and for clearer code
    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, width, topHeight);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, topHeight + gap, width, bottomHeight);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

}