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

    public static int BOARD_WIDTH = 1600;
    public static int BOARD_HEIGHT = 800;
    public BufferedImage backgroundImage;
    private BufferedImage backgroundEasy;
    private BufferedImage backgroundMedium;
    private BufferedImage backgroundHard;
    PlayerSprite playerSprite;
    public GameState gameState = GameState.MENU;
    List<PipeSprite> pipes = new ArrayList<>();
    int pipeSpawnCounter = 0;
    Font customFont;

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

        loadFont();
        loadBackgroundImage();
        drawButtons();
        createTimer();
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
        showScore(g);
    }

    private void initGame() {
        BufferedImage player = null;

        switch (difficulty) {
            case EASY -> backgroundImage = backgroundEasy;
            case MEDIUM -> backgroundImage = backgroundMedium;
            case HARD -> backgroundImage = backgroundHard;
        }

        try {
            player = switch (difficulty) {
                case EASY ->
                        ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Player/StyleBird2/BirdEasy.png")));
                case MEDIUM ->
                        ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Player/StyleBird2/BirdMed.png")));
                case HARD ->
                        ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Player/StyleBird2/BirdHard.png")));
            };
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Player image not found for difficulty: " + difficulty);
        }

        playerSprite = new PlayerSprite(BOARD_WIDTH / 2 - 500, BOARD_HEIGHT / 2 - 20, 70, 70, player);
        pipes.clear();
        pipeSpawnCounter = 0;
    }

    private void updatePlayer() {
        playerSprite.move();
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    private void updatePipes() throws IOException {
        pipeSpawnCounter++;

        var pipeInterval = 0;
        var speed = 0;
        var gap = 0;
        BufferedImage topPipeImg;
        BufferedImage bottomPipeImg;

        switch (difficulty) {
            case EASY:
                pipeInterval = 130;
                speed = 5;
                gap = 300;
                topPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeEasy.png")));
                bottomPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeEasy.png")));
                break;
            case MEDIUM:
                pipeInterval = 90;
                speed = 6;
                gap = 250;
                topPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeMed.png")));
                bottomPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeMed.png")));
                break;
            case HARD:
                pipeInterval = 50;
                speed = 10;
                gap = 250;
                topPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeHardTop.png")));
                bottomPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeHardBot.png")));
                break;
            default:
                pipeInterval = 150;
                speed = 4;
                gap = 320;
                topPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeEasy.png")));
                bottomPipeImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Tiles/Style 1/PipeEasy.png")));
                break;
        }


        if (pipeSpawnCounter >= pipeInterval) {
            pipes.add(new PipeSprite(BOARD_WIDTH, BOARD_HEIGHT, gap, speed, topPipeImg, bottomPipeImg));
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

    public void resetGame() throws IOException {
        gameState = GameState.MENU;
        backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/MenuBkg.png")));
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

    public void showBackButton(JButton backButton) {
        for (Component comp : getComponents()) {
            if (comp == backButton) {
                comp.setVisible(true);
            }
        }
    }

    public void drawButtons() {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton leaderButton = new JButton("LeaderBoard");
        JButton backButton = new JButton("Back");

        easyButton.setBounds(BOARD_WIDTH / 2 - 300, BOARD_HEIGHT / 2, 200, 40);
        mediumButton.setBounds(BOARD_WIDTH / 2 - 100, BOARD_HEIGHT / 2, 200, 40);
        hardButton.setBounds(BOARD_WIDTH / 2 + 100, BOARD_HEIGHT / 2, 200, 40);
        leaderButton.setBounds(BOARD_WIDTH / 2 - 100, BOARD_HEIGHT / 2 + 100, 200, 50);
        backButton.setBounds(BOARD_WIDTH / 2 - 100, BOARD_HEIGHT / 2 + 300, 200, 30);

        setLayout(null);
        add(easyButton);
        add(mediumButton);
        add(hardButton);
        add(leaderButton);
        add(backButton);

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

        leaderButton.addActionListener(_ -> {
            //show leader board
            hideMenuButtons();
            showBackButton(backButton);
        });

        backButton.addActionListener(_ -> {
            try {
                resetGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadFont() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                            Objects.requireNonNull(getClass().getResourceAsStream("/Flappy Bird Assets/Background/font.ttf")))
                    .deriveFont(Font.PLAIN, 32f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
        }
    }

    public void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/MenuBkg.png")));
            backgroundEasy = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/LevelEasy.png")));
            backgroundMedium = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/LevelMed.png")));
            backgroundHard = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/LevelHard.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Background image loading failed: " + e.getMessage());
        }
    }

    public void showScore(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", Font.PLAIN, 36)));

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(Color.WHITE);

        String scoreText = "Score: " + playerSprite.getScore();
        String spaceText = "Space: " + playerSprite.getSpace();
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(scoreText);
        int x = (BOARD_WIDTH - textWidth) / 2;
        int y = 50;

        g2d.drawString(scoreText, x, y);
        g2d.drawString(spaceText, x, y + fm.getAscent());
        g2d.dispose();
    }

    public void createTimer() {
        Timer timer = new Timer(1, _ -> {
            if (gameState == GameState.PLAYING && playerSprite != null) {
                updatePlayer();
                try {
                    updatePipes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (playerSprite.outOfBounds() || checkCollision()) {
                    saveScore();
                    try {
                        resetGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            repaint();
        });

        timer.start();
    }

    private void saveScore() {
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);

        if (playerName == null) {
            // Cancel pressed
            return;
        }

        if (playerName.trim().isEmpty()) {
            // OK pressed but nothing typed
            playerName = "Anonymous";
        }

        int score = playerSprite.getScore();
        String diff = difficulty.name();

        ScoreInfo scoreInfo = new ScoreInfo(playerName, score, diff);
        ScoreDAO dao = new ScoreDAO();
        dao.insertScore(scoreInfo);
    }


}