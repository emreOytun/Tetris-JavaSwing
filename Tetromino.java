
/**
* <h1>Tetromino Class</h1>
* This class implements needed functions for a classic Tetromino.
*
* @author Emre Oytun
*/
public class Tetromino implements Cloneable {
    private static final int ROWSIZE = 4;
    private static final int COLSIZE = 4;

    private int active_rowNum;
    private int active_colNum;

    private int startX;
    private int startY;

    private char[][] blocks = null;

    /**
     * Constructs the tetromino with default type O.
     */
    public Tetromino() {
        active_rowNum = 0;
        active_colNum = 0;

        initMemory();
        init(TetrominoType.O);
    }

    /**
     * Constructs the tetromino with the given type.
     * @param TetrominoType
     */
    public Tetromino(TetrominoType tetrominoType) {
        active_rowNum = 0;
        active_colNum = 0;

        startX = 0;
        startY = 0;

        initMemory();
        init(tetrominoType);
    }

    /**
     * Constructs the tetromino with the given type and given starting positions.
     * @param tetrominoType
     * @param startX
     * @param startY
     */
    public Tetromino(TetrominoType tetrominoType, int startX, int startY) {
        this(tetrominoType);
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * Deep clones the tetromino instance
     * @return Cloned tetromino
     */
    @Override
    public Object clone() {
        try {
            Tetromino copy = (Tetromino) super.clone();
            
            copy.initMemory();
            /* Deep copy the blocks array. */
            for (int i = 0; i < ROWSIZE; ++i) {
                for (int j = 0; j < COLSIZE; ++j) {
                    copy.blocks[i][j] = blocks[i][j];
                }
            }

            return copy;
        } catch (CloneNotSupportedException e) {
            e.getStackTrace();
        }
        
        return null;
    }

    /**
     * Checks if the given Object is equal to the tetromino
     * @param Object - If it's not in type of Tetromino, then it's not equal
     * @return Boolean - True if it's equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;

        Tetromino oth = (Tetromino) obj;
        if (!(active_rowNum == oth.active_rowNum && active_colNum == oth.active_colNum)) return false;

        boolean blocksEqual = true;
        for (int i = 0; i < active_rowNum && blocksEqual; ++i) {
            for (int j = 0; j < active_colNum && blocksEqual; ++j) {
                if (blocks[i][j] != oth.blocks[i][j]) blocksEqual = false;
            }
        }
        
        return blocksEqual;
    }

    @Override
    public int hashCode() {
        return active_rowNum*active_colNum + startX*startY;
    }

    /**
     * Prints the tetromino to the terminal
     */
    public void print() {
		for (int i = 0; i < active_rowNum; ++i) {
			for (int j = 0; j < active_colNum; ++j) {
				System.out.print(blocks[i][j]);
			}
			System.out.println();
		}
	}

    /**
     * Rotates the tetromino according to the given direction
     * @param DirectionType - Rotation direction
     */
    public void rotate(DirectionType direction) {
        char[][] rotatedBlocks = new char[ROWSIZE][COLSIZE];
        
        if (direction == DirectionType.Right) {
            for (int i = 0; i < ROWSIZE; ++i) {
                for (int j = 0; j <  COLSIZE; ++j) {
                    rotatedBlocks[j][COLSIZE-1-i] = blocks[i][j];
                }
            }
        }

        else {
            for (int i = 0; i < ROWSIZE; ++i) {
                for (int j = 0; j < COLSIZE; ++j) {
                    rotatedBlocks[ROWSIZE-1-j][i] = blocks[i][j];
                }
            }
        }

        blocks = rotatedBlocks;
        setActualBlocks();
        
        /* Swap active row and col numbers. */
        int temp = active_rowNum;
        active_rowNum = active_colNum;
        active_colNum = temp;

    } 

    /**
     * Changes the tetromino's type.
     * @param TetrominoType - Type that tetromino will be changed into.
     */
    public void changeType(TetrominoType tetrominoType) {
		/* Empty the data. */
		for (int i = 0; i < ROWSIZE; ++i) {
			for (int j = 0; j < COLSIZE; ++j) {
				blocks[i][j] = ' ';
			}
		}
		
		/* Initialize the new type. */
		init(tetrominoType);
	}

    /**
     * @return Int - Start X position
     */
    public int getStartX()  { return startX; };
	
    /**
     * 
     * @return Int - Start Y position
     */
    public int getStartY()  { return startY; };


    /**
     * Changes the start positions of the tetromino.
     * @param Int - startX
     * @param Int - startY
     */
	public void setStartPosition(int startX, int startY) {
		setStartX(startX);
		setStartY(startY);
	} 

    /**
     * 
     * @param Int - startX 
     */
	public void setStartX(int startX_) {
		startX = startX_;
	}

    /**
     * 
     * @param Int - startY
     */
	public void setStartY(int startY_) {
		startY = startY_;
	}

    /**
     * 
     * @return Int - Active column number of tetromino block.
     */
	public int getActiveColNum()  { return active_colNum; }

    /**
     * 
     * @return Int - Active row number of tetromino block.
     */
	public int getActiveRowNum()  { return active_rowNum; }

    /**
     * @return Char[][] - Copy of tetromino block.
     */
    public char[][] getBlock() {
        char[][] copyBlocks = new char[ROWSIZE][COLSIZE];
        for (int i = 0; i < ROWSIZE; ++i) {
            for (int j = 0; j < COLSIZE; ++j) {
                copyBlocks[i][j] = blocks[i][j];
            }
        }
        return blocks;
    }

    /**
     * Lowers the tetromino incrementing 1 its startX.
     */
	public void lower() {
		setStartX(startX+1);
	}

    /**
     * Moves the tetromino right incrementing 1 its startY.
     */
	public void goRight() {
		setStartY(startY+1);
	}

    /**
     * Moves the tetromino left decrementing 1 its startY.
     */
	public void goLeft() {
		setStartY(startY-1);
	}

    /**
     * 
     * @param Int - Index to get the line.
     * @return Char[] - Line according to the given index.
     * @throws IllegalArgumentException - When the given index is out of boundaries.
     */
    public char[] get(int idx) throws IllegalArgumentException {
        if (idx < 0 || idx >= active_rowNum) {
			new IllegalArgumentException("Index is out of boundaries for tetromino.");
		}
		return blocks[idx];
    }

    /**
     * Finds and returns the left most bottom indexes of the tetromino.
     * @return Int[] - Left most bottom indexes, [0] -> x, [1] -> y.
     */
    public int[] getLeftMostBottomIndexes() {
        boolean isFound = false;
        int lastRowIndex = active_rowNum - 1;
        int i;
        for (i = 0; i < blocks[lastRowIndex].length && !isFound;) {
            if (blocks[lastRowIndex][i] != ' ') isFound = true;
            else ++i;
        }	
        
        return new int[] {lastRowIndex, i};
    }

    private void initMemory() {
        blocks = new char[ROWSIZE][COLSIZE];
        for (int i = 0; i < ROWSIZE; ++i) {
            for (int j = 0; j < COLSIZE; ++j) {
                blocks[i][j] = ' ';
            }
        }
    }

    private void init(TetrominoType tetrominoType) {
        if (tetrominoType == TetrominoType.I) {
            blocks[0][0] = blocks[0][1] = blocks[0][2] = blocks[0][3] = 'I';
            setActualBlocks();
            
            active_rowNum = 1;
            active_colNum = 4;        
        }

        else if (tetrominoType == TetrominoType.O) {
            blocks[0][0] = blocks[0][1] = blocks[1][0] = blocks[1][1] = 'O';
            setActualBlocks();

            active_rowNum = 2;
            active_colNum = 2;        
        }

        else if (tetrominoType == TetrominoType.T) {
            blocks[1][0] = blocks[1][1] = blocks[1][2] = blocks[0][1] = 'T';
            setActualBlocks();
            
            active_rowNum = 2;
            active_colNum = 3;
            
        }

        else if (tetrominoType == TetrominoType.J) {
            blocks[0][0] = blocks[1][0] = blocks[1][1] = blocks[1][2] = 'J';
            setActualBlocks();
            
            active_rowNum = 2;
            active_colNum = 3;
            
        }

        else if (tetrominoType == TetrominoType.L) {
            blocks[0][2] = blocks[1][0] = blocks[1][1] = blocks[1][2] = 'L';
            setActualBlocks();
           
            active_colNum = 3;
            active_rowNum = 2;
            
        }

        else if (tetrominoType == TetrominoType.S) {
            blocks[0][1] = blocks[0][2] = blocks[1][0] = blocks[1][1] = 'S';
            setActualBlocks();
            
            active_rowNum = 2;
            active_colNum = 3;
            
        }

        else if (tetrominoType == TetrominoType.Z) {
            blocks[0][0] = blocks[0][1] = blocks[1][1] = blocks[1][2] = 'Z';
            setActualBlocks();
            
            active_rowNum = 2;
            active_colNum = 3;
            
        }
        
    }

    /* Checks if the left col of the blocks array is empty or not. */
	private boolean isLeftColEmpty() {
		boolean isEmpty = true;
		for (int i = 0; i < ROWSIZE; ++i) {
			if (blocks[i][0] != ' ') isEmpty = false;
		}		
		return isEmpty;
	}

	/* Checks if the top row of the blocks array is empty or not. */
	private boolean isTopRowEmpty() {
		boolean isEmpty = true;
		for (int j = 0; j < COLSIZE; ++j) {
			if (blocks[0][j] != ' ') isEmpty = false;
		}
		return isEmpty;
	}

	/* Shifts the blocks matrix 1 col toward left  */
	private void shiftActualBlocksToLeft() {
		for (int i = 0; i < ROWSIZE; ++i) {
			for (int j = 1; j < COLSIZE; ++j) {
				blocks[i][j-1] = blocks[i][j];
				blocks[i][j] = ' ';
			}
		}
	}

	/* Shifts the blocks matrix 1 column upward. */
	private void shiftActualBlocksToUp() {
		for (int i = 0; i < COLSIZE; ++i) {
			for (int j = 1; j < ROWSIZE; ++j) {
				blocks[j-1][i] = blocks[j][i];
				blocks[j][i] = ' ';
			}
		}
	}

	/* Sets the blocks array using the rotateMatrix so that blocks array has the actual blocks in the top mostleft part. */
	private void setActualBlocks() {
		while (isTopRowEmpty()) shiftActualBlocksToUp();
		while (isLeftColEmpty()) shiftActualBlocksToLeft();
	}
    
    
}

enum TetrominoType {
    I, O, T, J, L, S, Z
}

enum DirectionType {
    Left, Right
}
