package dto;

/**
 * Position DTO
 *
 * @author  Julian Kotrba
 */
public class Position {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
