package flappygame;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        super("FlappyBird Clone");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);
        setContentPane(new GameBoard());
        pack();
        setLocationRelativeTo(null);
    }
}