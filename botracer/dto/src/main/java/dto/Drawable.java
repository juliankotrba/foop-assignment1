package dto;

import java.io.Serializable;

abstract class Drawable implements Serializable {

	private static final long serialVersionUID = 1L;

	int index;
	Position position;

	Drawable(int index, Position position) {
		this.index = index;
		this.position = position;
	}
}
