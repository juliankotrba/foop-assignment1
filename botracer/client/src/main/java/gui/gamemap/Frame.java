package gui.gamemap;

class Frame {

	private double size = 0;
	private double offsetX = 0;
	private double offsetY = 0;

	double getSize() {
		return size;
	}

	void setSize(double size) {
		this.size = size;
	}

	double getOffsetX() {
		return offsetX;
	}

	void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	double getOffsetY() {
		return offsetY;
	}

	void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	@Override
	public String toString() {
		return "tileSize: " + size + ", offset: (x: " + offsetX + ", y: " + offsetY + ")";
	}
}
