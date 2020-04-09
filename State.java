// CLASS: State
//
// Author: Xu Yang
//
// REMARKS: a helper class for evaluating heuristic value for a given board
//          also can generate and return a board's valid next-move board
//
//-----------------------------------------
public class State {
    private Status[][] board;
    private Status currPlayer;
    private Status winner;
    private int size;
    //------------------------------------------------------
    // State
    //
    // PURPOSE:    constructor of State
    // PARAMETERS: board and last player
    // Returns: null
    //------------------------------------------------------
    public State(Status[][] inBoard,Status lastPlayer){
        this.size = inBoard.length;
        this.board = new Status[size][size];
        for(int i = 0; i < size; i++){
            System.arraycopy(inBoard[i], 0, this.board[i], 0, size);
        }
        this.currPlayer = switchSide(lastPlayer);
    }
    //generate node of a given col(or so called son state)
    public State generateNode(int col){
        if(verifyCol(col)){
            State newNode = new State(this.board,currPlayer);
            newNode.Move(col);
            return newNode;
        }else{
            return null;
        }
    }
    private void Move(int col){
        int posn = drop(col);
        this.board[posn][col] =  currPlayer;
    }
    private int drop(int col) {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
    private boolean verifyCol(int col) {
        return (col >= 0 && col < board[0].length && board[0][col] == Status.NEITHER);
    }
    //when a new son-state is generated, switch its side so it play in its best interest( switch between min and max)
    private Status switchSide(Status lastPlayer){
        if(lastPlayer == Status.ONE){
            return Status.TWO;
        }else
            return Status.ONE;
    }

    //calculate and sum up the heuristic value
    public int heuristicValue(){
        int human = threeInRow(Status.ONE)*10 + twoInRow(Status.ONE)*4+ inMiddle(Status.ONE);
        int ai = threeInRow(Status.TWO)*10 + twoInRow(Status.TWO)*4 + inMiddle(Status.TWO);
        if(fourInRow()){
            if(Status.ONE == winner) human += 100000;
            else ai += 100000;
        }
        return ai-human;
    }
    //------------------------------------------------------
    // inMiddle
    //
    // PURPOSE: the drop in the middle area have more chance(obviously?)
    // PARAMETERS: current player
    // Returns: int
    //------------------------------------------------------

    private int inMiddle(Status selectPlayer){
        int times = 0;
        int j = 0;
        if(size%2 == 0){
            j = size/2-1;
        }else{
            j = (size-1)/2;
        }
        for(int i =0;i<size;i++){
            if(this.board[i][j] == selectPlayer){
                times++;
            }
        }
        return times;
    }
    //------------------------------------------------------
    // validMove
    //
    // PURPOSE: a valid next move for a possible win move
    // PARAMETERS: int tow and int col
    // Returns: boolean
    //------------------------------------------------------
    public boolean validMove(int row,int col){
        if ((row <= -1) || (col <= -1) || (row > size-2) || (col > size-1)) {
            return false;
        }
        return true;
    }
    //------------------------------------------------------
    // FourInRow
    //
    // PURPOSE: detect if OOOO or XXXX (a win state)
    // PARAMETERS: null
    // Returns: boolean
    //------------------------------------------------------
    public boolean fourInRow() {
        //horizon
        for (int i=size-1; i>=0; i--) {
            for (int j=0; j<size-3; j++) {
                if (board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] && board[i][j] != Status.NEITHER) {
                    winner = board[i][j];
                    return true;
                }
            }
        }
        //vertical
        for (int i=size-1; i>=3; i--) {
            for (int j=0; j<size; j++) {
                if (board[i][j] == board[i-1][j] && board[i][j] == board[i-2][j] && board[i][j] == board[i-3][j] && board[i][j] != Status.NEITHER) {
                    winner = board[i][j];
                    return true;
                }
            }
        }
        //descend.
        for (int i=0; i<size-3; i++) {
            for (int j=0; j<size-4; j++) {
                if (board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] && board[i][j] != Status.NEITHER) {
                    winner = board[i][j];
                    return true;
                }
            }
        }

        //ascend.
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (validMove(i-3,j+3)) {
                    if (board[i][j] == board[i-1][j+1] && board[i][j] == board[i-2][j+2] && board[i][j] == board[i-3][j+3] && board[i][j] != Status.NEITHER) {
                        winner = board[i][j];
                        return true;
                    }
                }
            }
        }
        winner = Status.NEITHER;
        return false;
    }
    //------------------------------------------------------
    // ThreeInRow
    //
    // PURPOSE: detect how many OOO or XXX
    // PARAMETERS: null
    // Returns: int
    //------------------------------------------------------
    private int threeInRow(Status selectPlayer){
        int times = 0;
        // Check for 3 in a row, horizon
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (validMove(i, j + 2)) {
                    if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }

        // Check for 3  in a row, vertical
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (validMove(i - 2, j)) {
                    if (board[i][j] == board[i - 1][j] && board[i][j] == board[i - 2][j] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 in a row, ↘
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (validMove(i + 2, j + 2)) {
                    if (board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 in a row, ↗
        for (int i = 0; i < size-1; i++) {
            for (int j = 0; j < size; j++) {
                if (validMove(i - 2, j + 2)) {
                    if (board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }

        return times;

    }
    //------------------------------------------------------
    // TwoInRow
    //
    // PURPOSE: detect how many OO or XX
    // PARAMETERS: null
    // Returns: int
    //------------------------------------------------------
    public int twoInRow(Status selectPlayer) {

        int times = 0;
        //horizon
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (validMove(i, j + 1)) {
                    if (board[i][j] == board[i][j + 1] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }
        //vertical
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (validMove(i - 1, j)) {
                    if (board[i][j] == board[i - 1][j] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }
        //↘
        for (int i = 0; i < size-1; i++) {
            for (int j = 0; j < size; j++) {
                if (validMove(i + 1, j + 1)) {
                    if (board[i][j] == board[i + 1][j + 1] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }
        //↗
        for (int i = 0; i < size-1; i++) {
            for (int j = 0; j < size; j++) {
                if (validMove(i - 1, j + 1)) {
                    if (board[i][j] == board[i - 1][j + 1] && board[i][j] == selectPlayer) {
                        times++;
                    }
                }
            }
        }
        return times;
    }
}
