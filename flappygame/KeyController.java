// KeyController class for handling key pressing
package flappygame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    private final GameBoard gameBoard;
    public KeyController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // if 'R' is pressed & the game is playing, we reset the game
        if (e.getKeyCode() == KeyEvent.VK_R && gameBoard.gameState == GameBoard.GameState.PLAYING) {
            try {
                gameBoard.resetGame();
            }
            catch (Exception ex) {
                System.err.println("Failed to reset game!");
            }
        }
        // if the game is playing & we press 'SPACE' the PlayerSprite will jump
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameBoard.gameState == GameBoard.GameState.PLAYING) {
            gameBoard.getPlayerSprite().jump();
        }
        // if we press 'ESCAPE' the game will quit
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && (gameBoard.gameState == GameBoard.GameState.PLAYING || gameBoard.gameState == GameBoard.GameState.MENU)) {
            System.exit(0);
        }
    }

    // no need to handle key releases
    @Override
    public void keyReleased(KeyEvent e) {}
}

