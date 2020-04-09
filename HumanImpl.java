public class HumanImpl implements Human {
    private GameLogic gl;
    public HumanImpl(GameLogic gl){
        this.gl = gl;
    }
    @Override
    public void setAnswer(int col) {
        gl.setAnswer(col);
    }
}