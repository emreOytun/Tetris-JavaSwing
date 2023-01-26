
/**
* <h1>AbstractTetris Class</h1>
* This class implements some of the needed functions to create the game tetris.
*
* @author Emre Oytun
*/
public abstract class AbstractTetris {
   
    /* Private:  */
    private boolean status;
    private int totalMoves;

    private boolean isCurTetrominoActive;
    protected Tetromino curTetromino;

    /* Protected:  */
    protected int rowNum;
    protected int colNum;

    protected abstract void print();
    
	public abstract char[][] getMap();

	
	protected abstract void checkLines();

    protected abstract void addTetromino(Tetromino t);
    protected abstract void deleteTetromino(Tetromino t);
    protected abstract boolean canBeAdded(Tetromino t);
    protected abstract boolean checkHorizantalMovement(Tetromino comingT, int newYPos);

    /* Public:  */

	/**
     * Constructs the tetris with the given parameters.
     * @param rowNumber
     * @param colNumber
     */
    AbstractTetris(int rowNumber, int colNumber) throws IllegalArgumentException {
        if (rowNumber < 0 || colNumber < 0) {
            throw new IllegalArgumentException("Arguments row number: " + rowNumber + " col number: " + colNumber + " are invalid.");
        }

        this.rowNum = rowNumber;
        this.colNum = colNumber;
        this.status = true;
        this.isCurTetrominoActive = false;
        this.totalMoves = 0;
    }

	/**
     * 1) Adjusts the new tetromino's position to be at the top row in the middle, and checks if it can be added to the map. <br>
	 * 2) If it can be added then new tetromino is active for game; if it's not then the new tetromino is not active.
     * @param Tetromino - New tetromino to be added.
     */
    public void add(Tetromino newTetromino) {
        curTetromino = newTetromino;
        
        /* Set the position of the tetromino to the middle of the game map. */
		curTetromino.setStartPosition(0, (int) ( (colNum-curTetromino.getActiveColNum()) / 2));
		
		if (!canBeAdded(curTetromino)) {
			status = false;
			isCurTetrominoActive = false;
		}
		else isCurTetrominoActive = true;
    }

	/**
	 * Lower the current tetromino if it is available; or add it to the board if it hits the bottom.
	 */
	public void lowerTetromino() {
		if (status && isCurTetrominoActive) {
			Tetromino copy = (Tetromino) curTetromino.clone();
			copy.lower();
			if (canBeAdded(copy)) {
				curTetromino.lower();
				++totalMoves;
			}
			else {
				addTetromino(curTetromino);
				isCurTetrominoActive = false;
				checkLines();
			}
			
		}
	}

	/**
     * Moves the tetromino left if this move can be done.
     */
	public void moveTetrominoLeft() {
		if (status && isCurTetrominoActive) {
			Tetromino copy = (Tetromino) curTetromino.clone();
			copy.goLeft();
			if (canBeAdded(copy)) {
				curTetromino.goLeft();
				++totalMoves;
			}
		}
	}

	/**
     * Moves the tetromino right if this move can be done.
     */
	public void moveTetrominoRight() {
		
		if (status && isCurTetrominoActive) {
			Tetromino copy = (Tetromino) curTetromino.clone();
			copy.goRight();
			if (canBeAdded(copy)) {
				curTetromino.goRight();
				++totalMoves;
			}
		}
	}

	/**
	 * It rotates the tetromino if rotation can be done.
	 */
	public void rotateTetromino() {
		if (status && isCurTetrominoActive) {
			Tetromino copy = (Tetromino) curTetromino.clone();
			copy.rotate(DirectionType.Right);
			if (canBeAdded(copy)) {
				curTetromino.rotate(DirectionType.Right);
			}
		}
	}
	
	/**
     * Draws the board with the current tetromino if there is an active current tetromino, or draws the board without a new tetromino otherwise.
     */
    public void draw() {
        if (isCurTetrominoActive) {
			addTetromino(curTetromino);
			print();
			deleteTetromino(curTetromino);
		}
		
		else {
			print();
		}
    }

	/**
     * 
     * @return Boolean - True if the game continues, false otherwise.
     */
    public boolean gameStatus() { return status; }

	/**
	 * 
	 * @return Boolean - True if currenet tetromino is active, false otherwise.
	 */
	public boolean currentTetrominoStatus() { return isCurTetrominoActive; }
	
	/**
	 * 
	 * @return Int - Number of total moves till now.
	 */
	public int numberOfMoves() { return totalMoves; }
	
	/**
	 * 
	 * @return Tetromino - Last moved tetromino.
	 */
	public Tetromino lastMove() { 
		if (totalMoves == 0) {
			throw new RuntimeException("There are no moves in the board.");
		}
		
		return (Tetromino) curTetromino.clone();
	}

}

