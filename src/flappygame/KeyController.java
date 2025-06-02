package flappygame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    private final GameBoard gameBoard;
    public KeyController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameBoard.gameState == GameBoard.GameState.MENU) {
            gameBoard.startGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameBoard.gameState == GameBoard.GameState.PLAYING) {
            gameBoard.getPlayerSprite().jump();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}

