// CLASS: GameLogicImpl
//
// Author: Xu Yang
//
// REMARKS: a implementation of GameLogic
//
//-----------------------------------------
import java.util.Arrays;
import java.util.Random;

public class GameLogicImpl implements GameLogic {
    private Player player1;
    private Player player2;
    private Status [][] board;
    Status curr = Status.NEITHER;
    int size;
    //------------------------------------------------------
    // GameLogicImpl
    //
    // PURPOSE: constructor of gameLogic
    // PARAMETERS: null
    // Returns: null
    //------------------------------------------------------
    public GameLogicImpl(){
        //get a random size
        this.size = new Random().nextInt(7) + 6;
        System.out.println("random size: "+size);
        //set the board
        board = new Status[size][size];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
        player1 = new HumanPlayer();
        player2 = new AIPlayer();
        player1.setInfo(size,this);
        player2.setInfo(size,this);
    }
    //------------------------------------------------------
    // gameStart
    //
    // PURPOSE:  start the game,set random first player
    // PARAMETERS: null
    // Returns: null
    //------------------------------------------------------
    public void gameStart(){
        int firstPlayer = (int)Math.round( Math.random()) ;
        if(firstPlayer == 0){
            System.out.println("you first, please move");
            curr = Status.ONE;
            player1.lastMove(-1);
        }else{
            System.out.println("AI first, please wait for at most 10 sec");
            curr = Status.TWO;
            player2.lastMove(-1);
        }
    }
    //get last move and set the board
    private void lastMove(int lastCol){
        int lastPosn = drop(lastCol);
        board[lastPosn][lastCol] = curr;
    }
    private int drop( int col) {
        int posn = 0;
        while (posn < this.board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
    @Override
    //------------------------------------------------------
    // setAnswer
    //
    // PURPOSE: called by AI or Human, set their answer, call next player's lastMove()
    // PARAMETERS: col
    // Returns: null
    //------------------------------------------------------
    public void setAnswer(int col) {
        //put into board
        this.lastMove(col);
        //check if win
        if(checkDraw()){
            player1.gameOver(Status.NEITHER);
        }else{
            Status winner = this.checkWinnier();
            if(winner == Status.NEITHER) {
            //next player
                if (curr == Status.ONE) {
                    curr = Status.TWO;
                    player2.lastMove(col);
                } else if (curr == Status.TWO) {
                    curr = Status.ONE;
                    player1.lastMove(col);
                }
            }else{
                player1.gameOver(winner);
            }  
        }
    }

    //------------------------------------------------------
    // checkDraw
    //
    // PURPOSE: check if the game is in draw
    // PARAMETERS: null
    // Returns: boolean
    //------------------------------------------------------
    private boolean checkDraw(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j] == Status.NEITHER){
                    return false;
                }
            }
        }
        return true;
    }
    //------------------------------------------------------
    // checkWinner
    //
    // PURPOSE: check winner, if their is a winner, return Status winner
    // PARAMETERS: null
    // Returns: status winner
    //------------------------------------------------------
    private Status checkWinnier(){
        Status ret = Status.NEITHER;
        Status temp = Status.ONE;
        do{
            //check vertical
            for(int i=0;i<size-3;i++){
                for(int j=0;j<size;j++){
                    if(this.board[i][j]==temp && this.board[i+1][j]==temp && this.board[i+2][j]==temp && this.board[i+3][j]==temp){
                        return temp;
                    }
                }
            }
            //check horizon
            for(int j=0;j<size-3;j++){
                for(int i=0;i<size;i++){
                    if(this.board[i][j]==temp && this.board[i][j+1]==temp && this.board[i][j+2]==temp && this.board[i][j+3]==temp){
                        return temp;
                    }
                }
            }
            //check ↘
            for (int i=3; i<size; i++){
                for (int j=3; j<size; j++){
                    if (this.board[i][j] == temp && this.board[i-1][j-1] == temp && this.board[i-2][j-2] == temp && this.board[i-3][j-3] == temp)
                        return temp;
                }
            }
            //check ↗
            for (int i=3; i<size; i++){
                for (int j=0; j<size-3; j++){
                    if (this.board[i][j] == temp && this.board[i-1][j+1] == temp && this.board[i-2][j+2] == temp && this.board[i-3][j+3] == temp)
                        return temp;
                }
            }
            if(temp == Status.TWO){
                temp = null;
            }else {
                temp = Status.TWO;
            }
        }while(temp != null);
        return ret;
    }
}
