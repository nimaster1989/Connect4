// CLASS: HumanPlayer
//
// Author: Xu Yang
//
// REMARKS: implementation of Player
//
//-----------------------------------------
public class HumanPlayer implements Player {
    private UI ui;
    @Override
    public void lastMove(int lastCol) {
        ui.lastMove(lastCol);
    }

    @Override
    public void gameOver(Status winner) {
        ui.gameOver(winner);
    }
    @Override
    //create a Human obj
    public void setInfo(int size, GameLogic gl) {
        Human theMan = new HumanImpl(gl);
        ui = new SwingGUI();
        //ui = new TextUI();
        ui.setInfo(theMan,size);
    }
}
