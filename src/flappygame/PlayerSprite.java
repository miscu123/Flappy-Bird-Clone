package flappygame;

import java.awt.image.BufferedImage;

public class PlayerSprite extends Sprite {
    /** Variables for player score and position */
    private int score = 0;
    private float dy = 0;

    /**
     * Constructor to initialize the player
     * @param x the x position of the player
     * @param y the y position of the player
     * @param width the width of the player sprite
     * @param height the height of the player sprite
     * @param image the image for the player sprite
     */
    public PlayerSprite(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    /**
     * Function to simulate the gravity (constant)
     */
    public void move() {
        float gravity = 1.3f;
        dy += gravity;

        float maxFallSpeed = 7.4f;
        if (dy > maxFallSpeed)
            dy = maxFallSpeed;

        y +=(int) dy;
    }

    /**
     * Functions that help get the player positions, make it jump, get/add score & reset the player
     */

    /**
     * Makes the player jump by setting upward velocity
     */
    public void jump() {
        dy = -16;
    }

    /**
     * Gets the current score of the player
     * @return the player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds points to the player's score
     * @param val the value to add to the score
     */
    public void addScore(int val) {
        score += val;
    }

    /**
     * Checks if the player is out of bounds (above or below screen)
     * @return true if the player is out of bounds, false otherwise
     */
    public boolean outOfBounds() {
        return y < 0 || y + height > GameBoard.BOARD_HEIGHT;
    }

    /**
     * Resets the player to initial state
     */
    public void reset() {
        dy = 0;
        y = GameBoard.BOARD_HEIGHT / 2 - height / 2;
        x = GameBoard.BOARD_WIDTH / 2 - 200;
        score = 0;
    }
}