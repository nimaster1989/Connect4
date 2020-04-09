// CLASS: Mini_Max
//
// Author: Xu Yang
//
// REMARKS:run mini max algorithm, return move with highest heuristic value
//
//-----------------------------------------
import static java.lang.Math.*;
public class Mini_Max {
    //a depth of 8 is most for mini-max, 7 is best considering waiting time
    private int depth = 7;
    private State currState;
    private int size;
    public Mini_Max(Status[][] board){
        this.size = board.length;
        currState = new State(board,Status.TWO);
    }
    //run mini-max and get each son-node's heuristic value
    public int bestMove(){
        int pos = -1;
        int max = Integer.MIN_VALUE;
        int[] value = new int[size];
        for(int x=0;x<size;x++){
            State Node = currState.generateNode(x);
            if(Node != null) {
                value[x] = runMiniMax(Node, depth - 1, false);
                //System.out.println("value "+x+" is "+value[x]);
                if(value[x] > max) {
                    max = value[x];
                    pos = x;
                }
            }
        }
        return pos;
    }
    // Pseudocode
//    function minimax(node, depth, maximizingPlayer) is
//    if depth = 0 or node is a terminal node then
//        return the heuristic value of node
//    if maximizingPlayer then
//    value := −∞
//            for each child of node do
//    value := max(value, minimax(child, depth − 1, FALSE))
//            return value
//    else (* minimizing currPlayer *)
//    value := +∞
//            for each child of node do
//    value := min(value, minimax(child, depth − 1, TRUE))
//            return value
    public int runMiniMax(State currState,int depth,boolean MaximizingPlayer) {
        //System.out.println("into recursion");
        if(depth == 0 || currState.fourInRow()){
            return currState.heuristicValue();
        }
        int value = 0;
        if(MaximizingPlayer){
            value = Integer.MIN_VALUE;
            for(int x=0;x<size;x++){
                State Node = currState.generateNode(x);
                if(Node != null) {
                    value = max(value, runMiniMax(Node, depth - 1, false));
                }
            }
        }else {
            value = Integer.MAX_VALUE;
            for(int x=0;x<size;x++){
                State Node = currState.generateNode(x);
                if(Node!=null) {
                    value = min(value, runMiniMax(Node, depth - 1, true));
                }
            }
        }
        return value;
    }
}
