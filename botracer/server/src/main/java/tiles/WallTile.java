package tiles;

import tiles.Tile;

public class WallTile extends Tile {

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String toString() {
        return "+";
    }
}
