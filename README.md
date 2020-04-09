# README for Comp2150A3
## 0.This is assignment of COMP2150, GUI files are provided by Professor Mike Domerazki
## 1.How to Compile
Run “javac A3.java”
## 2.How to Run and Play
Run “java A3”
If you see “you first, please move”, then you can make moves
If you see “AI first, please wait for at most 10 sec”, please wait for the AI to make its first move, it takes a few seconds (depends on the size of the board)

## 3.Comment of A3
### 3.1 AI algorithm
I implement the heuristic algorithm mini-max and alpha-beta-pruning that learned from COMP3190 to decide the next move of AI, given the depth I set for the algorithm, the AI is currently invincible, if markers wants to test with a weaker AI, you can change the depth in the Alpha_beta_pruning class. 
### 3.2 Helper class “State”
The class is made for evaluating the heuristic value of the given board, also can generate and return a board’s valid next-move board.
### 3.3 Change between two AI algorithm 
Change between two AI algorithm can be done by change the comment line in AIPlayer class, getNextMove() function, according to my personal test, alpha-beta-pruning can calculate one more depth comparing with normal min-max in the same time.

