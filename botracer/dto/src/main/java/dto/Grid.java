package dto;

import java.util.ArrayList;

public class Grid<E> extends ArrayList<E> {

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
