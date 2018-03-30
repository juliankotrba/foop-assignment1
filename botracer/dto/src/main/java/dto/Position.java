package dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Position DTO
 *
 * @author  Julian Kotrba
 */
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int width;
    private final int height;

    public Position(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return width == position.width &&
                height == position.height;
    }

    @Override
    public int hashCode() {

        return Objects.hash(width, height);
    }
}
