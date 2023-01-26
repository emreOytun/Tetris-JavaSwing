
/**
* <h1>Tetris Class</h1>
* Tetris class implements all needed functions to create the game tetris.
*
* @author Emre Oytun
*/
public class TetrisVector extends AbstractTetris {

    private char[][] map = null;

	/**
     * Constructs the tetris with the given parameters.
     * @param rowNum
     * @param colNum
     */
    public TetrisVector(int rowNum, int colNum) {
        super(rowNum, colNum);

        /* Initialize the map. */
        map = new char[rowNum][colNum];
        for (int i = 0; i < rowNum; ++i) {
            for (int j = 0; j < colNum; ++j) {
                map[i][j] = ' ';
            }
        }
    }

	/**
	 * Prints the tetris to the terminal.
	 */
    @Override
    protected void print() {
        for (int i = 0; i < rowNum; ++i) {
			for (int j = 0; j < colNum; ++j) {
				char ch = map[i][j];
				if (ch == ' ') System.out.print("*");
				else System.out.print(ch);
			}
			System.out.print("\n");
		}
    }

	/**
	 * Checks the lines to remove if there are full lines.
	 */
	@Override
	protected void checkLines() {

		for (int i = rowNum - 1; i >= 0; --i) {
			boolean isFull = true;
			for (int j = 0; j < colNum && isFull; ++j) {
				if (map[i][j] == ' ') isFull = false;
			}
		
			if (isFull) {
				deleteLine(i);
				++i;
			}
		}

	}

	/**
	 * Removes the given line.
	 * @param Int - lineIdx to be removed.
	 */
	private void deleteLine(int lineIdx) {
		for (int i = lineIdx - 1; i >= 0; --i) {
			for (int j = 0; j < colNum; ++j) {
				map[i+1][j] = map[i][j];
			}
		}
	}

    @Override
    protected void addTetromino(Tetromino t) {
        for (int i = 0; i < t.getActiveRowNum(); ++i) {
			for (int j = 0; j < t.getActiveColNum(); ++j) {
				if (t.get(i)[j] != ' ') map[i+t.getStartX()][j+t.getStartY()] = t.get(i)[j];
			}
		}
    }

    @Override
    protected void deleteTetromino(Tetromino t) {
        for (int i = 0; i < t.getActiveRowNum(); ++i) {
			for (int j = 0; j < t.getActiveColNum(); ++j) {
				if (t.get(i)[j] != ' ') map[i+t.getStartX()][j+t.getStartY()] = ' ';
			}
		}
    }

    @Override
    protected boolean canBeAdded(Tetromino t) {
        int start_x = t.getStartX();
		int start_y= t.getStartY();
		
		boolean canAdded = true;
		for (int i = 0; i < t.getActiveRowNum() && canAdded; ++i) {
			for (int j = 0; j < t.getActiveColNum() && canAdded; ++j) {
				if (i+start_x<0 || i+start_x>=rowNum || j+start_y<0 || j+start_y>=colNum)	canAdded = false;
				else if (map[i+start_x][j+start_y] != ' ' && t.get(i)[j] != ' ') canAdded = false; 		
			}
		}

		return canAdded;
    }

    @Override
    protected boolean checkHorizantalMovement(Tetromino comingT, int newYPos) {
        Tetromino newT = (Tetromino) comingT.clone();
		int start_y = newT.getStartY();
		
		boolean isPlaced = false;
		boolean isDone = false;
		
		while (!isPlaced && !isDone) {
			if (!canBeAdded(newT)) isDone = true;
			else {
			
				if (newT.getStartY() == newYPos) {
					while (!newT.equals(comingT)) {
						newT.rotate(DirectionType.Right);
					}
					if (canBeAdded(newT)) isPlaced = true; 		
					else isDone = true;
				}
				
				else {
				
					if (newYPos < start_y) newT.goLeft();
					else newT.goRight();
					
					boolean canPlaced = false;
						
					if (canBeAdded(newT)) canPlaced = true;
					else {
						for (int i = 0; i < 3 && !canPlaced; ++i) {
							newT.rotate(DirectionType.Right);
							if (canBeAdded(newT)) canPlaced = true;
						}		
					}		
				
					if (!canPlaced) isDone = true;
				}
			}
		}
		
		return isPlaced;
    }

	private void copyMap(char[][] source, char[][] destination) {
		for (int i = 0; i < source.length; ++i) {
			for (int j = 0; j < source[i].length; ++j) {
				destination[i][j] = source[i][j];
			}
		}
	}

	@Override
	public char[][] getMap() {
		char[][] renderedMap = new char[rowNum][colNum];
		
		if (currentTetrominoStatus()) {
			addTetromino(curTetromino);
			copyMap(map, renderedMap);
			deleteTetromino(curTetromino);
		}

		else copyMap(map, renderedMap);
		
		return renderedMap;
	}
    
}

