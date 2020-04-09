// CLASS: AIPlayer
//
// Author: Xu Yang
//
// REMARKS: an implementation of Player
//
//-----------------------------------------
import java.util.Arrays;
public class AIPlayer implements Player {
    private Status [][] board;
    private int size;
    private GameLogic gl;


    @Override
    //called by gameLogic and make next move by call getNextMove()
    public void lastMove(int lastCol) {
        if(lastCol != -1) {
            int lastPosn = drop(lastCol);
            board[lastPosn][lastCol] = Status.ONE;
        }
        int next = getNextMove();
        gl.setAnswer(next);
    }
    //create a mini-max or alpha-beta-pruning obj and get its best move
    private int getNextMove(){
        //Mini_Max miniMax = new Mini_Max(board);
        //int move = miniMax.bestMove();
        Alpha_beta_pruning ab = new Alpha_beta_pruning(board);
        int move = ab.bestMove();
        int lastPosn = drop(move);
        board[lastPosn][move] = Status.TWO;
        return move;
    }
    @Override
    //remain empty because don't need to print the win state
    public void gameOver(Status winner) {}
    private int drop( int col) {
        int posn = 0;
        while (posn < size && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
    @Override
    //set the size,board and game logic
    public void setInfo(int size, GameLogic gl) {
        this.gl = gl;
        this.size = size;
        board = new Status[size][size];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
    }
}
