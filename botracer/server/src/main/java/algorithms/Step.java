package algorithms;

/**
 * enum for possible steps a bot can do
 */
public enum Step {
    RIGHT,LEFT,UP,DOWN,NONE;
    private Step opposite;
    static {
        RIGHT.opposite=LEFT;
        LEFT.opposite=RIGHT;
        UP.opposite=DOWN;
        DOWN.opposite=UP;
    }

    /**
     * get the opposite step of a step
     *
     * @return opposite step
     */
    public Step getOpposite(){
        return opposite;
    }
}
