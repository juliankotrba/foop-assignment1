package dto;

import java.io.Serializable;

/**
 * Position DTO
 *
 * @author  Julian Kotrba
 */
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int x;
    private final int y;

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
