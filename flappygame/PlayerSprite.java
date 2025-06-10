package flappygame;

import java.awt.image.BufferedImage;

public class PlayerSprite extends Sprite {
    // variables for player score and position
    private int score = 0;
    private float dy = 0;

    // constructor to initialize the player
    public PlayerSprite(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    // function to simulate the gravity (constant)
    public void move() {
        float gravity = 1.2f;
        dy += gravity;

        float maxFallSpeed = 7;
        if (dy > maxFallSpeed)
            dy = maxFallSpeed;

        y +=(int) dy;
    }

    // functions that help get the player positions, make it jump, get/add score & reset the player
    public void jump() {
        dy = -16;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int val) {
        score += val;
    }

    public boolean outOfBounds() {
        return y < 0 || y + height > GameBoard.BOARD_HEIGHT;
    }

    public void reset() {
        dy = 0;
        y = GameBoard.BOARD_HEIGHT / 2 - height / 2;
        x = GameBoard.BOARD_WIDTH / 2 - 200;
        score = 0;
    }
}