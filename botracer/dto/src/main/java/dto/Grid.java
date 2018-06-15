package dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Grid
 *
 * Storage for objects in a coordinate system
 *
 * @author David Walter
 */
public class Grid<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int width;
	private final int height;

	public Grid(int width, int height) {
		super();
		ensureCapacity(width * height);
		this.width = width;
		this.height = height;
	}

	public int capacity() {
		return width * height;
	}

	@Override
	public boolean add(E e) {
		if (size() < capacity()) {
			return super.add(e);
		}

		return false;
	}

	/**
	 * Returns the object at the specified coordinate
	 * @param x value on X-axis
	 * @param y value on Y-axis
	 * @return object at the specified coordinate
	 */
	public E get(int x, int y) {
		return this.get(x + y * width);
	}

	/**
	 * Returns the object at the specified coordinate
	 * @param position position in the coordinate system
	 * @return object at the specified coordinate
	 */
	public E get(Position position) {
		return get(position.getWidth(), position.getHeight());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void smalltalk() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile  = (Tile) this.get(x, y);
				TileType type = tile.getType();
				if (type == TileType.WALL) {
					int downY = y + 1;
					if (downY < height) {
						Tile down = (Tile) this.get(x, downY);
						if (down.getType() == TileType.WALL) {
							System.out.println("game addItem: WallTile new at: " + x + "@" + y + ".");
							continue;
						}
					}

					System.out.println("game addItem: FrontWallTile new at: " + x + "@" + y + ".");
				} else if (type == TileType.EXIT) {
					System.out.println("game addItem: ExitTile new at: " + x + "@" + y + ".");
				}

			}
		}
	}
}
