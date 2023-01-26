import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameArea extends JPanel implements KeyListener {

    public final int ROW_NUM;
    public final int COL_NUM;
    public final int GAMEAREA_WIDTH;
    public final int GAMEAREA_HEIGHT;
    public static final int BLOCK_SIZE = 20;
    

    private static final int FPS = 60;
    private static final int delay = 1000 / FPS;
    private static final int normalGameTime = 300;
    private static final int fastGameTime = 50;

    private int delayTime = normalGameTime;
    private long startTime = 0;
    private Timer looper = null;
    
    private AbstractTetris tetris = null;
    
    private int deltaX = 0;
    private boolean rotate = false;
    private boolean paused = false;

    /**
     * Constructs the game area with the given parameters.
     * @param rowNumber
     * @param colNumber
     */
    public GameArea(int rowNum, int colNum) {
        /* Set size of the game area panel. */
        ROW_NUM = rowNum;
        COL_NUM = colNum;

        GAMEAREA_WIDTH = BLOCK_SIZE * COL_NUM;
        GAMEAREA_HEIGHT = BLOCK_SIZE * ROW_NUM;

        setSize(GAMEAREA_WIDTH, GAMEAREA_HEIGHT);
        setBounds(0, 0, GAMEAREA_WIDTH, GAMEAREA_HEIGHT);
        setLayout(null);

        tetris = new TetrisVector(ROW_NUM, COL_NUM);

        looper = new Timer(delay, new MainLoopListener());
        looper.start();
    }

    private class MainLoopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           
            /* If time passed since the last time tetromine lowered is greater than delayTime, then check for horizantal movement 
             * and lower tetromino.
             */
            if (paused == false && tetris.gameStatus() == true) {    
                
                if (System.currentTimeMillis() - startTime > delayTime) {
                    startTime = System.currentTimeMillis();

                    if (tetris.currentTetrominoStatus()) {
                        if (rotate == true) {
                            tetris.rotateTetromino();
                            rotate = false;
                        }

                        /* Move tetromino horizantally. */
                        int moveCount = Math.abs(deltaX);
                        for (int i = 0; i < moveCount; ++i) {
                            if (deltaX < 0) tetris.moveTetrominoLeft();
                            else tetris.moveTetrominoRight();
                        }
                        deltaX = 0;

                        /* Move tetromino vertically. */
                        tetris.lowerTetromino();
                    }
                    else {
                        tetris.add(randomTet()); 
                    }    
                    
                }

                /* Else only check for horizantal movement */
                else {
                    if (tetris.currentTetrominoStatus()) {
                        if (rotate == true) {
                            tetris.rotateTetromino();
                            rotate = false;
                        }

                        /* Move tetromino horizantally. */
                        int moveCount = Math.abs(deltaX);
                        for (int i = 0; i < moveCount; ++i) {
                            if (deltaX < 0) tetris.moveTetrominoLeft();
                            else tetris.moveTetrominoRight();
                        }
                        deltaX = 0;

                    }
                    else {
                        tetris.add(randomTet()); 
                    } 
                }
                  
                /* Render */
                repaint();
            }
        }
    }

    public void stopGame() {
        paused = true;
    }

    public void startGame() {
        paused = false;
    }

    public void restartGame() {
        tetris = new TetrisVector(ROW_NUM, COL_NUM);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Paint the area to the black. */
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        /* Paint lines with white. */
        g.setColor(Color.white);
        for (int row = 0; row < ROW_NUM + 1; ++row) {
            g.drawLine(0, BLOCK_SIZE*row, BLOCK_SIZE*COL_NUM,BLOCK_SIZE*row);
        }

        for (int col = 0; col < COL_NUM + 1; ++col) {
            g.drawLine(col*BLOCK_SIZE, 0, col*ROW_NUM, BLOCK_SIZE*ROW_NUM);
        }
        
        char[][] map = tetris.getMap();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                
                /* Set colors according to the blocks type.. */
                if (map[i][j] == 'L') {
                    g.setColor(Color.red);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }
            
                else if (map[i][j] == 'I') {
                    g.setColor(Color.blue);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }
            
                else if (map[i][j] == 'Z') {
                    g.setColor(Color.pink);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }

                else if (map[i][j] == 'S') {
                    g.setColor(Color.orange);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }

                else if (map[i][j] == 'T') {
                    g.setColor(Color.GREEN);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }

                else if (map[i][j] == 'O') {
                    g.setColor(Color.WHITE);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }

                else if (map[i][j] == 'J') {
                    g.setColor(Color.MAGENTA);
                    g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }

            }
        }
    
        if (tetris.gameStatus() == false) {
            JOptionPane optionPane = new JOptionPane();
            JDialog dialog = optionPane.createDialog(null, "Game Over");
            dialog.setModal(false);
            optionPane.setMessage("Game Over");
            
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
    }

    private static Tetromino randomTet() {
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(7) + 1;
        
        if (randomNum == 1) return new Tetromino(TetrominoType.I);
        if (randomNum == 2) return new Tetromino(TetrominoType.O);
        if (randomNum == 3) return new Tetromino(TetrominoType.T);
        if (randomNum == 4) return new Tetromino(TetrominoType.L);
        if (randomNum == 5) return new Tetromino(TetrominoType.J);
        if (randomNum == 6) return new Tetromino(TetrominoType.S);
        if (randomNum == 7) return new Tetromino(TetrominoType.Z);
        
        return new Tetromino(TetrominoType.I);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            delayTime = fastGameTime;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            deltaX = 1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            deltaX = -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            rotate = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            delayTime = normalGameTime;
        }

    }

}

