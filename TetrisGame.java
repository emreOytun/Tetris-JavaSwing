import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisGame {

    public static final int ROW_NUM = 20;
    public static final int COL_NUM = 10;
    public static final int BLOCK_SIZE = 20;

    public static final int SIDEPANEL_WIDTH = 200;

    public static final int FRAME_WIDTH = BLOCK_SIZE * COL_NUM + SIDEPANEL_WIDTH;
    public static final int FRAME_HEIGHT = BLOCK_SIZE * ROW_NUM + 2*BLOCK_SIZE;

    public static final int GAMEAREA_WIDTH = BLOCK_SIZE * COL_NUM;
    public static final int GAMEAREA_HEIGHT = BLOCK_SIZE * ROW_NUM;

    private GameArea gameArea = null;

    /**
     * Opens the TetrisJava frame and starts the game.
     */
    public TetrisGame() {
        
        /* Our main gameFrame. */
        JFrame gameFrame = new JFrame("TetrisJava");
    
        gameFrame.setSize(TetrisGame.FRAME_WIDTH, TetrisGame.FRAME_HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);

        /* Create the GameArea class's instance. */
        gameArea = new GameArea(ROW_NUM, COL_NUM);
        gameFrame.addKeyListener(gameArea);
        gameFrame.add(gameArea);

        /* sidePanel to add control buttons. */
        JPanel sidePanel = new JPanel();
        sidePanel.setBounds(GAMEAREA_WIDTH, 0, SIDEPANEL_WIDTH, GAMEAREA_HEIGHT);
        sidePanel.setLayout(null);  /* If we don't do that, since it uses a default layout; it is not possible to add a button properly. */

        /* "PAUSE" button will be inside sidePanel. */
        JButton bt1 = new JButton("START");
        bt1.setBounds(GAMEAREA_WIDTH + 20, GAMEAREA_HEIGHT - 80, 80, 20);
        bt1.addActionListener(e -> {
            gameArea.startGame();
        });
        bt1.addKeyListener(gameArea);
        sidePanel.add(bt1); // add button to the side panel.

        JButton bt2 = new JButton("PAUSE");
        bt2.setBounds(GAMEAREA_WIDTH + 20, GAMEAREA_HEIGHT - 50, 80, 20);
        bt2.addActionListener(e -> {
            gameArea.stopGame();
        });
        bt2.addKeyListener(gameArea);
        sidePanel.add(bt2); // add button to the side panel.

        JButton bt3 = new JButton("RESTART");
        bt3.setBounds(GAMEAREA_WIDTH + 20, 30, 100, 20);
        bt3.addActionListener(e -> {
            gameArea.restartGame();
        });
        bt3.addKeyListener(gameArea);
        sidePanel.add(bt3); // add button to the side panel.


        gameFrame.add(sidePanel);

        gameFrame.setVisible(true);
    }

}

