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

	private int width;
	private int height;

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
	 *
	 * @param x value on X-axis
	 * @param y value on Y-axis
	 * @return object at the specified coordinate
	 */
	public E get(int x, int y) {
		return this.get(x + y * width);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
