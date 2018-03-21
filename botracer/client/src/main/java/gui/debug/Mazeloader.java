package gui.debug;

import dto.Grid;
import dto.Position;
import dto.Tile;
import dto.TileType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Mazeloader {

	public static Mazeloader shared = new Mazeloader();

	private Mazeloader() {}

	public Grid<Tile> load(URL url) {
		if (url == null) { return null; }
		try {
			return load(url.toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Grid<Tile> load(String path) {
		try {
			List<String> stringList = Files.readAllLines(Paths.get(path));

			Grid<Tile> grid = new Grid<>(stringList.get(0).length(), stringList.size());

			for (int y = 0; y < stringList.size(); y += 1) {
				String line = stringList.get(y);
				for (int x = 0; x < line.length(); x += 1) {
					char c = line.charAt(x);
					if (c == '*') {
						grid.add(new Tile(TileType.WALL, new Position(x, y)));
					} else if (c == 'E') {
						grid.add(new Tile(TileType.EXIT, new Position(x, y)));
					} else {
						grid.add(new Tile(TileType.DEFAULT, new Position(x, y)));
					}
				}
			}
			return grid;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
