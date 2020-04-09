// CLASS: Alpha_beta_pruning
//
// Author: Xu Yang
//
// REMARKS:run alpha_beta_pruning algorithm, return move with highest heuristic value
//
//-----------------------------------------
import static java.lang.Math.*;
public class Alpha_beta_pruning {
    //a depth of 8 is best for a-b considering human player waiting times
    private int depth = 8;
    private State currState;
    private int size;
    public Alpha_beta_pruning(Status[][] board){
        this.size = board.length;
        currState = new State(board,Status.TWO);
        //if size too big, take too much time,so reduce depth
        if(this.size > 9){
            this.depth -- ;
        }
    }
    //run mini-max and get each son-node's heuristic value
    public int bestMove(){
        int pos = -1;
        int max = Integer.MIN_VALUE;
        int[] value = new int[size];
        for(int x=0;x<size;x++){
            State Node = currState.generateNode(x);
            if(Node != null) {
                value[x] = runAlpha_beta_pruning(Node, depth - 1,Integer.MIN_VALUE,Integer.MAX_VALUE, false);
                //System.out.println("heuristic value of"+x+" is "+value[x]);
                if(value[x] > max) {
                    max = value[x];
                    pos = x;
                }
            }
        }
        return pos;
    }
//    function alphabeta(node, depth, α, β, maximizingPlayer) is
//    if depth = 0 or node is a terminal node then
//        return the heuristic value of node
//    if maximizingPlayer then
//    value := −∞
//            for each child of node do
//    value := max(value, alphabeta(child, depth − 1, α, β, FALSE))
//    α := max(α, value)
//            if α ≥ β then
//                break (* β cut-off *)
//            return value
//    else
//    value := +∞
//            for each child of node do
//    value := min(value, alphabeta(child, depth − 1, α, β, TRUE))
//    β := min(β, value)
//            if α ≥ β then
//                break (* α cut-off *)
//            return value
    private int runAlpha_beta_pruning(State currState,int depth,int alpha,int beta,boolean MaximizingPlayer) {
        if(depth == 0 || currState.fourInRow()){
            //f*(n) = g*(n) + h*(n)
            //g*(n): cost from first state to the the current state
            //explanation: if the sub-note have more than one way to win, g*(n) can make sure the most less step win move is taken
            //h*(n): cost from current state to the goal state
            return  depth * 1000 + currState.heuristicValue();
        }
        int value;
        if(MaximizingPlayer){
            value = Integer.MIN_VALUE;
            for(int x=0;x<size;x++){
                State Node = currState.generateNode(x);
                if(Node != null) {
                    value = max(value, runAlpha_beta_pruning(Node, depth - 1,alpha,beta, false));
                    alpha = max(alpha,value);
                    if (beta <= alpha) break;
                }
            }
        }else {
            value = Integer.MAX_VALUE;
            for(int x=0;x<size;x++){
                State Node = currState.generateNode(x);
                if(Node!=null) {
                    value = min(value, runAlpha_beta_pruning(Node, depth - 1,alpha,beta, true));
                    beta = min(beta,value);
                    if(beta <= alpha) break;
                }
            }
        }
        return value;
    }
}
