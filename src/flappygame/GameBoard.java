package flappygame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.Objects;


public class GameBoard extends JPanel {

    public static int BOARD_WIDTH = 1200;
    public static int BOARD_HEIGHT = 800;
    public BufferedImage backgroundImage;
    PlayerSprite playerSprite;
    public GameState gameState = GameState.MENU;
    List<PipeSprite> pipes = new ArrayList<>();
    int pipeSpawnCounter = 0;

    public enum GameState {
        MENU,
        PLAYING,
        GAME_OVER
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    private Difficulty difficulty = Difficulty.EASY;

    public GameBoard(){
        super();
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        requestFocusInWindow();
        addKeyListener(new KeyController(this));
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/Background1.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("No background detected !");
        }
        drawButtons();

        Timer timer = new Timer(20, _ -> {
            if (gameState == GameState.PLAYING && playerSprite != null) {
                updatePlayer();
                updatePipes();

                if (playerSprite.outOfBounds() || checkCollision())
                    resetGame();

            }
            repaint();
        });


        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (gameState == GameState.MENU) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press ENTER to play!", BOARD_WIDTH / 2 - 115, 200);
            return;
        }

        for (PipeSprite pipe : pipes)
            pipe.draw(g);

        if (playerSprite != null)
            playerSprite.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + playerSprite.getScore(), 10, 30);
        g.drawString("Space pressed " + playerSprite.getSpace() + " times", 10, 20);
    }

    private void initGame(){
        playerSprite = new PlayerSprite(BOARD_WIDTH / 2 - 200, BOARD_HEIGHT / 2 - 20, 80, 80);
        pipes.clear();
        pipeSpawnCounter = 0;
    }

    private void updatePlayer() {
        playerSprite.move();
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    private void updatePipes() {
        pipeSpawnCounter++;

        var pipeInterval = 0;
        var speed = 0;
        var gap = 0;

        switch (difficulty) {
            case EASY:
                pipeInterval = 70;
                speed = 6;
                gap = 300;
                break;
            case MEDIUM:
                pipeInterval = 50;
                speed = 8;
                gap = 250;
                break;
            case HARD:
                pipeInterval = 30;
                speed = 15;
                gap = 250;
                break;
            default:
                pipeInterval = 60;
                speed = 7;
                gap = 320;
                break;
        }


        if (pipeSpawnCounter >= pipeInterval) {
            pipes.add(new PipeSprite(BOARD_WIDTH, BOARD_HEIGHT, gap, speed));
            pipeSpawnCounter = 0;
    }

        List<PipeSprite> toRemove = new ArrayList<>();
        for (PipeSprite pipe : pipes) {
            pipe.move();
            if (!pipe.isPassed() && playerSprite.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed(true);
                playerSprite.addScore(1);
            }
            if (pipe.getX() + 80 < 0) {
                toRemove.add(pipe);
            }
        }
        pipes.removeAll(toRemove);
    }


    public void startGame() {
        initGame();
        gameState = GameState.PLAYING;
    }

    public void resetGame() {
        gameState = GameState.MENU;
        if (playerSprite != null)
            playerSprite.reset();
        pipes.clear();
        showMenuButtons();
        repaint();
    }

    private boolean checkCollision() {
        Rectangle playerBounds = playerSprite.getBounds();

        for (PipeSprite pipe : pipes) {
            if (playerBounds.intersects(pipe.getTopBounds()) || playerBounds.intersects(pipe.getBottomBounds()))
                return true;
        }

        return false;
    }

    private void hideMenuButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                comp.setVisible(false);
            }
        }
    }

    private void showMenuButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                comp.setVisible(true);
            }
        }
    }

    public void drawButtons() {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        easyButton.setBounds(BOARD_WIDTH / 2 - 300, BOARD_HEIGHT / 2, 200, 40);
        mediumButton.setBounds(BOARD_WIDTH / 2 - 100, BOARD_HEIGHT / 2, 200, 40);
        hardButton.setBounds(BOARD_WIDTH / 2 + 100, BOARD_HEIGHT / 2, 200, 40);

        setLayout(null);
        add(easyButton);
        add(mediumButton);
        add(hardButton);

        easyButton.addActionListener(_ -> {
            difficulty = Difficulty.EASY;
            startGame();
            hideMenuButtons();
        });

        mediumButton.addActionListener(_ -> {
            difficulty = Difficulty.MEDIUM;
            startGame();
            hideMenuButtons();
        });

        hardButton.addActionListener(_ -> {
            difficulty = Difficulty.HARD;
            startGame();
            hideMenuButtons();
        });
    }
}