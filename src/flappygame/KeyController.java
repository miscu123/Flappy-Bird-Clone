/**
 * Handles keyboard input for the Flappy Bird game.
 * Implements KeyListener to respond to key presses and manage game controls.
 */
package flappygame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    private final GameBoard gameBoard;

    /**
     * Constructs a KeyController tied to a specific GameBoard.
     *
     * @param gameBoard The GameBoard instance this controller will manage
     */
    public KeyController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Not used in this implementation.
     * @param e The KeyEvent object
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles all key press events for game control:
     * - R key resets the game during gameplay
     * - SPACE makes the player jump during gameplay
     * - ESCAPE quits the game from either gameplay or menu
     *
     * @param e The KeyEvent object containing key press information
     */
    @Override
    public void keyPressed(KeyEvent e) {
        /** if 'R' is pressed & the game is playing, we reset the game */
        if (e.getKeyCode() == KeyEvent.VK_R && gameBoard.gameState == GameBoard.GameState.PLAYING) {
            try {
                gameBoard.resetGame();
            }
            catch (Exception ex) {
                System.err.println("Failed to reset game!");
            }
        }
        /** if the game is playing & we press 'SPACE' the PlayerSprite will jump */
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameBoard.gameState == GameBoard.GameState.PLAYING) {
            gameBoard.getPlayerSprite().jump();
        }
        /** if we press 'ESCAPE' the game will quit */
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && (gameBoard.gameState == GameBoard.GameState.PLAYING || gameBoard.gameState == GameBoard.GameState.MENU)) {
            System.exit(0);
        }
    }

    /**
     * Not used in this implementation.
     * @param e The KeyEvent object
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}