package algorithms;

public enum Step {
    RIGHT,LEFT,UP,DOWN,NONE;
    private Step opposite;
    static {
        RIGHT.opposite=LEFT;
        LEFT.opposite=RIGHT;
        UP.opposite=DOWN;
        DOWN.opposite=UP;
    }

    public Step getOpposite(){
        return opposite;
    }
}
