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

    /**
     * Constructor for loading a pipe with certain specifications
     * @param x the x position of the pipe
     * @param height the total height of the screen
     * @param gap the gap between top and bottom pipes
     * @param speed the speed at which the pipe moves
     * @param topPipeImg the image for the top pipe
     * @param bottomPipeImg the image for the bottom pipe
     */
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

    /**
     * Function to move the pipes to the left
     */
    public void move() {
        x -= speed;
    }

    /**
     * Function to draw the pipes
     * @param g the Graphics object to draw with
     */
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

    /**
     * Functions created to gather info about the pipes
     * Created to ease our work with game logic and for clearer code
     */

    /**
     * Gets the bounding rectangle for the top pipe
     * @return Rectangle representing the top pipe bounds
     */
    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, width, topHeight);
    }

    /**
     * Gets the bounding rectangle for the bottom pipe
     * @return Rectangle representing the bottom pipe bounds
     */
    public Rectangle getBottomBounds() {
        return new Rectangle(x, topHeight + gap, width, bottomHeight);
    }

    /**
     * Gets the x position of the pipe
     * @return the x coordinate of the pipe
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the width of the pipe
     * @return the width of the pipe
     */
    public int getWidth() {
        return width;
    }

    /**
     * Checks if the pipe has been passed by the player
     * @return true if the pipe has been passed, false otherwise
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Sets whether the pipe has been passed by the player
     * @param passed true if the pipe has been passed, false otherwise
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

}