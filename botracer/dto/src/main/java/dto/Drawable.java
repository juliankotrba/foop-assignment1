package dto;

import java.io.Serializable;

abstract class Drawable implements Serializable {

	private static final long serialVersionUID = 1L;

	Position position;

	Drawable(Position position) {
		this.position = position;
	}
}
