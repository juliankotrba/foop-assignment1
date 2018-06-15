package game;

import algorithms.Algorithm;
import algorithms.Memory;
import algorithms.Step;

import java.util.Objects;

public class Player {
	private Memory memory;
	private Algorithm algorithm;
	private int height;
	private int width;
	private int id;
	private String name;
	private boolean ownedByPlayer;
	private boolean isReady;

	public Player() {
	}


	public Player(int id, String name, int height, int width, Algorithm algorithm) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.width = width;
		this.algorithm = algorithm;
		this.ownedByPlayer = false;
		this.memory = new Memory();
		this.isReady = false;
	}

	public Player(int id, String name, int height, int width, Algorithm algorithm, boolean ownedByPlayer) {
		this(id, name, height, width, algorithm);
		this.ownedByPlayer = ownedByPlayer;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void nextStep(GameBoard gameBoard) {
		Step step = algorithm.nextStep(memory, gameBoard, width, height);
		switch (step) {
			case UP:
				height--;
				break;
			case DOWN:
				height++;
				break;
			case LEFT:
				width--;
				break;
			case RIGHT:
				width++;
				break;
		}
		memory.setLastStep(step);
		memory.addVisited(gameBoard.getTile(height, width));
		gameBoard.getTile(height, width).enters(this);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isOwnedByPlayer() {
		return ownedByPlayer;
	}

	public void setOwnedByPlayer(boolean ownedByPlayer) {
		this.ownedByPlayer = ownedByPlayer;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean ready) {
		isReady = ready;
	}

	@Override
	public String toString() {
		return "Player{" +
				"memory=" + memory +
				", algorithm=" + algorithm +
				", width=" + width +
				", height=" + height +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return id == player.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
