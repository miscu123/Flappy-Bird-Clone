package flappygame;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class PlayerSprite extends Sprite {
    private int score = 0;
    private int dy = 0;
    private int space = 0;

    public PlayerSprite(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Player/StyleBird1/Bird-Init.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Player image not found!");
        }
    }

    @Override
    public void move() {
        int gravity = 1;
        dy += gravity;
        int maxFallSpeed = 10;
        if (dy > maxFallSpeed)
            dy = maxFallSpeed;

        y += dy;
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
