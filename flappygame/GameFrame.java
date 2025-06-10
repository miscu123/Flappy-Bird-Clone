/**
 * The GameFrame class initializes the canvas and creates the game board
 */
package flappygame;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        super("FlappyBird Clone");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(true);
        setContentPane(new GameBoard());
        pack();
        setLocationRelativeTo(null);
    }
}