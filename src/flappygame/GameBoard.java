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

/**
 * The GameBoard class is the game engine class
 * Here the game is running and working as it should
 */
public class GameBoard extends JPanel {

    /**
     * The width of the game board
     */
    public static int BOARD_WIDTH = 1600;
    /**
     * The height of the game board
     */
    public static int BOARD_HEIGHT = 800;

    /**
     * Background image for current game state
     */
    public BufferedImage backgroundImage;
    private BufferedImage backgroundEasy;
    private BufferedImage backgroundMedium;
    private BufferedImage backgroundHard;

    /**
     * The player character sprite
     */
    PlayerSprite playerSprite;

    /**
     * Current game state
     */
    public GameState gameState = GameState.MENU;

    /**
     * Possible game states
     */
    public enum GameState {
        MENU,
        PLAYING
    }

    /**
     * List of active pipes in the game
     */
    List<PipeSprite> pipes = new ArrayList<>();
    /**
     * Counter for pipe spawning timing
     */
    int pipeSpawnCounter = 0;

    /**
     * Custom font used in the game
     */
    Font customFont;

    /**
     * Panel for displaying leaderboard
     */
    private JPanel leaderboardPanel;

    /**
     * Current game difficulty level
     */
    private Difficulty difficulty = Difficulty.EASY;

    /**
     * Available difficulty levels
     */
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    /**
     * Constructs the game board and initializes components.
     * Sets up key listeners, loads resources, and creates game timer.
     */
    public GameBoard() {
        super();
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        requestFocusInWindow();
        addKeyListener(new KeyController(this));

        loadFont(20);
        loadBackgroundImage();
        drawButtons();
        createTimer();
    }

    /**
     * Paints the game components on the board.
     *
     * @param g The Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (gameState == GameState.MENU) {
            showInstructions(g);
            return;
        }

        for (PipeSprite pipe : pipes)
            pipe.draw(g);

        if (playerSprite != null)
            playerSprite.draw(g);

        g.setColor(Color.WHITE);
        showScore(g);
    }

    /**
     * Initializes game components based on current difficulty.
     * Loads appropriate player sprite and background.
     */
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

    /**
     * Updates player position and state.
     */
    private void updatePlayer() {
        playerSprite.move();
    }

    /**
     * Gets the player sprite instance.
     *
     * @return The current player sprite
     */
    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    /**
     * Updates all pipes in the game.
     * Handles spawning, movement, scoring and removal of pipes.
     *
     * @throws IOException if pipe images cannot be loaded
     */
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

    /**
     * Starts the game by initializing components and changing state to PLAYING.
     */
    public void startGame() {
        initGame();
        gameState = GameState.PLAYING;
    }

    /**
     * Resets the game to menu state.
     * Clears all game objects and resets variables.
     *
     * @throws IOException if menu background image cannot be loaded
     */
    public void resetGame() throws IOException {
        gameState = GameState.MENU;
        backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Flappy Bird Assets/Background/MenuBkg.png")));
        if (playerSprite != null)
            playerSprite.reset();
        pipes.clear();
        showMenuButtons();
        repaint();
    }

    /**
     * Determines whether the player collided with a pipe or went off-screen
     *
     * @return true if collision occurred, false otherwise
     */
    private boolean checkCollision() {
        Rectangle playerBounds = playerSprite.getBounds();

        for (PipeSprite pipe : pipes) {
            if (playerBounds.intersects(pipe.getTopBounds()) || playerBounds.intersects(pipe.getBottomBounds()))
                return true;
        }

        return false;
    }

    /**
     * Hides the menu buttons when we press play or want to see the leaderboard
     */
    private void hideMenuButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                comp.setVisible(false);
            }
        }
    }

    /**
     * Hides the leaderboard panel
     */
    private void hideLeaderboard() {
        for (Component comp : getComponents()) {
            if (comp == leaderboardPanel)
                comp.setVisible(false);
        }
    }

    /**
     * Shows the menu buttons
     */
    private void showMenuButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                comp.setVisible(true);
            }
        }
    }

    /**
     * Shows the back button
     *
     * @param backButton the back button to show
     */
    public void showBackButton(JButton backButton) {
        for (Component comp : getComponents()) {
            if (comp == backButton) {
                comp.setVisible(true);
            }
        }
    }

    /**
     * Draws the buttons on screen
     */
    public void drawButtons() {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton leaderButton = new JButton("Leaderboard");
        JButton backButton = new JButton("Back");

        /** coordinates and size */
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

        /** create individual events for whenever a specific button is pressed */
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
            showLeaderboard();
            hideMenuButtons();
            showBackButton(backButton);
        });

        backButton.addActionListener(_ -> {
            hideLeaderboard();
            showMenuButtons();
        });
    }

    /**
     * Loads custom font with specified size
     *
     * @param size the font size to use
     */
    public void loadFont(int size) {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                            Objects.requireNonNull(getClass().getResourceAsStream("/Flappy Bird Assets/Background/font.ttf")))
                    .deriveFont(Font.PLAIN, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
        }
    }

    /**
     * Loads background images for the game
     */
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

    /**
     * Displays the player's score while playing
     *
     * @param g the Graphics object to draw with
     */
    public void showScore(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", Font.PLAIN, 36)));

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(Color.WHITE);

        String scoreText = "Score: " + playerSprite.getScore();
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(scoreText);
        int x = (BOARD_WIDTH - textWidth) / 2;
        int y = 70;

        g2d.drawString(scoreText, x, y);
        g2d.dispose();
    }

    /**
     * Displays instructions in the main menu
     *
     * @param g the Graphics object to draw with
     */
    public void showInstructions(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", Font.PLAIN, 36)));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(Color.WHITE);

        String diff = "Select a difficulty to play ↓";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(diff);
        int x = (BOARD_WIDTH - textWidth) / 2;
        int y = 350;

        g2d.drawString(diff, x, y);

        g2d.dispose();
    }

    /**
     * Function to update the player and the pipes at regular intervals
     * Also created to check if the player lost or not and if so, to save the score and reset the game
     */
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

    /**
     * Function for saving the score to the database
     */
    private void saveScore() {
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);

        if (playerName == null) {
            /** Cancel pressed */
            return;
        }

        if (playerName.trim().isEmpty()) {
            /** OK pressed but nothing typed */
            playerName = "Anonymous";
        }

        int score = playerSprite.getScore();
        String diff = difficulty.name();

        ScoreInfo scoreInfo = new ScoreInfo(playerName, score, diff);
        ScoreDAO dao = new ScoreDAO();
        dao.insertScore(scoreInfo);
    }

    /**
     * Function to show the leaderboard in real time
     * Leaderboard refreshed every time we load it
     */
    private void showLeaderboard() {
        if (leaderboardPanel != null) {
            this.remove(leaderboardPanel);
        }

        ScoreDAO scoreDAO = new ScoreDAO();
        List<ScoreInfo> scores = scoreDAO.getAllScores();

        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BorderLayout());
        leaderboardPanel.setBackground(Color.LIGHT_GRAY);

        int panelWidth = 600;
        int panelHeight = 250;

        int x = (BOARD_WIDTH - panelWidth) / 2;
        int y = (BOARD_HEIGHT - panelHeight) / 2;

        leaderboardPanel.setBounds(x, y, panelWidth, panelHeight);

        JTextArea leaderboardArea = getJTextArea(scores);
        leaderboardPanel.add(new JScrollPane(leaderboardArea), BorderLayout.CENTER);

        this.setLayout(null);
        this.add(leaderboardPanel);
        leaderboardPanel.setVisible(true);
        this.repaint();
    }

    /**
     * Function to show the leaderboard
     *
     * @param scores the list of scores to display
     * @return a JTextArea containing the formatted leaderboard
     */
    private static JTextArea getJTextArea(List<ScoreInfo> scores) {
        JTextArea leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder(" Player Name                          | Score                | Difficulty\n");
        sb.append("----------------------------------------------------------------------------------\n");
        for (ScoreInfo score : scores) {
            sb.append(String.format(" %-10s                           | %-4d                 | %-10s\n",
                    score.getName(), score.getScore(), score.getDifficulty()));
        }
        leaderboardArea.setText(sb.toString());
        return leaderboardArea;
    }
}