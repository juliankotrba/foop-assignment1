package debug;

import dto.Grid;
import dto.Position;
import dto.Tile;
import dto.TileType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * MazeLoader.java
 * Loads a Maze from a file
 *
 * @author David Walter
 */
public class MazeLoader {

	public static final MazeLoader shared = new MazeLoader();

	private MazeLoader() {
	}

	/**
	 * Loads a maze from the URL
	 *
	 * @param url location of the maze
	 * @return Grid<Tile> representation of the maze
	 */
	public Grid<Tile> load(URL url) {
		if (url == null) {
			return null;
		}
		try {
			return load(new File(url.toURI()).getAbsolutePath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads a maze from the path
	 *
	 * @param path location of the maze
	 * @return Grid<Tile> representation of the maze
	 */
	public Grid<Tile> load(String path) {
		try {
			List<String> stringList = Files.readAllLines(Paths.get(path));

			Grid<Tile> grid = new Grid<>(stringList.get(0).length(), stringList.size());

			for (int y = 0; y < stringList.size(); y += 1) {
				String line = stringList.get(y);
				for (int x = 0; x < line.length(); x += 1) {
					char c = line.charAt(x);
					switch (c) {
						case '*':
							grid.add(new Tile(TileType.WALL, new Position(x, y)));
							break;
						case 'E':
							grid.add(new Tile(TileType.EXIT, new Position(x, y)));
							break;
						default:
							grid.add(new Tile(TileType.DEFAULT, new Position(x, y)));
							break;
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
