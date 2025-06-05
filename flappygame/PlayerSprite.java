package flappygame;

import java.awt.image.BufferedImage;

public class PlayerSprite extends Sprite {
    private int score = 0;
    private float dy = 0;
    private int space = 0;

    public PlayerSprite(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    @Override
    public void move() {
        float gravity = 1.5f;
        dy += gravity;

        float maxFallSpeed = 10;
        if (dy > maxFallSpeed)
            dy = maxFallSpeed;

        y +=(int) dy;
    }


    public void jump() {
        dy = -15;
        addSpace();
    }

    public int getSpace() {
        return space;
    }

    public void addSpace() {
        space++;
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
        space = 0;
    }

}
