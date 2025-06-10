package flappygame;

import javax.swing.*;

/**
 * Main class used for running the game
 * Initializes the game frame
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
    }
}