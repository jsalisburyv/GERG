package map;

import graphics.tilemap.Tile;

import java.util.Random;

/**
 * Class to create custom tiles for the map
 * @author Jonathan Salisbury
 */
public class Autotiling {

    private static final int SIZE = 3;

    /**
     * Method that assigns each tile its right type
     * @param map map to be assigned
     * @return Tile matrix representing the map
     */
    public static Tile[][] assignTileTypes(int[][] map) {
        Random rand = new Random();
        int numRows = map.length, numCols = map[0].length;
        Tile[][] tileTypes = new Tile[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(map[i][j] == 0 ) {
                    if(rand.nextFloat() > 0.02) tileTypes[i][j] = TileType.FLOOR;
                    else tileTypes[i][j] = TileType.FLOOR2;
                } else {
                    tileTypes[i][j] = getWallTileType(map, i, j);
                }
            }
        }
        return tileTypes;
    }

    /**
     * Method of obtaining the type of a wall tile.
     * @param map Map
     * @param row row of the tile
     * @param col col of the tile
     * @return Tile type of the Wall
     */
    private static Tile getWallTileType(int[][] map, int row, int col) {
        int bitmask = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int ni = row + i - 1;
                int nj = col + j - 1;
                if (ni >= 0 && ni < map.length && nj >= 0 && nj < map[0].length) {
                    bitmask |= map[ni][nj] << (SIZE * SIZE - (i * SIZE + j) - 1);
                }
            }
        }
        return switch (bitmask) {
            case 0b011_111_111, 0b010_110_000                                               -> TileType.WALL_TOP_LEFT;
            case 0b100_111_111, 0b000_111_111, 0b001_111_111                                -> TileType.WALL_TOP;
            case 0b010_011_000, 0b010_011_011, 0b110_111_111                                -> TileType.WALL_TOP_RIGHT;
            case 0b111_011_011, 0b011_011_011, 0b011_011_111                                -> TileType.WALL_LEFT;
            case 0b111_110_110, 0b110_110_110, 0b110_110_111, 0b011_010_010, 0b010_010_011  -> TileType.WALL_RIGHT;
            case 0b000_111_011, 0b000_110_010, 0b111_111_011                                -> TileType.WALL_BOTTOM_LEFT;
            case 0b000_111_100, 0b000_111_001, 0b111_111_100, 0b111_111_000, 0b111_111_001  -> TileType.WALL_BOTTOM;
            case 0b000_011_010, 0b011_011_010, 0b000_111_110, 0b111_111_110                 -> TileType.WALL_BOTTOM_RIGHT;
            case 0b000_110_110, 0b000_110_111                                               -> TileType.WALL_TOP_AND_RIGHT;
            case 0b000_011_011, 0b000_011_111, 0b001_011_011                                -> TileType.WALL_TOP_AND_LEFT;
            case 0b110_110_000, 0b111_110_000                                               -> TileType.WALL_BOTTOM_AND_RIGHT;
            case 0b011_011_000, 0b111_011_000, 0b011_011_001                                -> TileType.WALL_BOTTOM_AND_LEFT;
            case 0b010_010_000                                                              -> TileType.WALL_RIGHT_BOTTOM_LEFT;
            case 0b000_011_000                                                              -> TileType.WALL_BOTTOM_LEFT_TOP;
            case 0b000_010_010                                                              -> TileType.WALL_LEFT_TOP_RIGHT;
            case 0b000_110_000                                                              -> TileType.WALL_TOP_RIGHT_BOTTOM;

            case 0b011_111_000 -> row == map.length - 1 ? TileType.WALL_TOP_LEFT: TileType.WALL_TOP_LEFT_AND_BOT;
            case 0b100_111_000 -> row == map.length - 1 ? TileType.WALL_TOP : TileType.WALL_TOP_AND_BOTTOM;
            case 0b001_111_000 -> row == map.length - 1 ? TileType.WALL_TOP : TileType.WALL_TOP_AND_BOTTOM;
            case 0b110_111_000 -> row == map.length - 1 ? TileType.WALL_TOP_RIGHT: TileType.WALL_TOP_RIGHT_AND_BOT;
            case 0b010_110_110 -> col == map[0].length - 1 ? TileType.WALL_TOP_LEFT : TileType.WALL_TOP_LEFT_AND_RIGHT;
            case 0b110_010_010 -> col == map[0].length - 1 ? TileType.WALL_LEFT : TileType.WALL_LEFT_AND_RIGHT;
            case 0b010_010_110 -> col == map[0].length - 1 ? TileType.WALL_LEFT : TileType.WALL_LEFT_AND_RIGHT;
            case 0b110_110_010 -> col == map[0].length - 1 ? TileType.WALL_BOTTOM_LEFT : TileType.WALL_BOT_LEFT_AND_RIGHT;
            case 0b010_010_010-> {
                if(col == 0) yield TileType.WALL_RIGHT;
                else if(col == map[0].length - 1) yield TileType.WALL_LEFT;
                else yield TileType.WALL_LEFT_AND_RIGHT;
            }
            case 0b000_111_000 -> {
                if(row == 0) yield TileType.WALL_BOTTOM;
                else if(row == map.length - 1) yield TileType.WALL_TOP;
                else yield TileType.WALL_TOP_AND_BOTTOM;
            }
            default -> TileType.EMPTY;
        };
    }
}
