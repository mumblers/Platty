package mumblers.platty.world;

import mumblers.platty.Player;
import mumblers.platty.graphics.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius on 19-8-2015.
 */
public class World {

    private Player player;
    private List<WorldListener> listeners = new ArrayList<>();

    public static String[] defaultWorld = {
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000",
            "11111111000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000000000000000001001100110110000001111000000000000000000000000000000000",
            "00000000000000000000000000000000000000000000000000011000000000000000000000000000000000000000000000000000000",
            "11111111111111111111111111111111111111111111111111111000000111111110011111111111111111111111111111111111111",
    };

    private boolean[][] blocks;/* = new boolean[][]{
            {false, false, false, false, false, false, false, false},
            {false, true, true, true, false, true, false, false},
            {false, true, true, true, false, false, false, false},
            {false, true, false, true, false, false, true, false},
            {false, false, false, false, false, false, true, false},
            {false, false, true, false, false, false, true, false},
            {false, true, true, true, false, false, false, false},
            {false, false, true, false, false, true, true, true}
    };*/

    //these vars can not changes because than the WorldBlockSprite should also update
    private int blockWidth;// = blocks[0].length;
    private int blockHeight;// = blocks.length;
    private static final char WALL_TILE = '1';

    public World(Input input) {
        blockWidth = defaultWorld[0].length();
        blockHeight = defaultWorld.length;
        blocks = new boolean[blockHeight][blockWidth];
        makeBlocksFromString(defaultWorld);
        player = new Player(input);
    }

    private void makeBlocksFromString(String[] world) {
        for (int y = 0; y < blockHeight; y++) {
            for (int x = 0; x < blockWidth; x++) {
                blocks[y][x] = world[y].charAt(x) == WALL_TILE;
            }
        }
    }

    public boolean blockAt(int row, int col) {
        if (row < 0 || col < 0 || row >= blockHeight || col >= blockWidth)
            return false;
        return blocks[row][col];
    }

    public void setBlockAt(int row, int col, boolean hasBlock) {
        blocks[row][col] = hasBlock;
        for (WorldListener listener : listeners) {
            listener.blockUpdated();
        }
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
        for (WorldListener listener : listeners) {
            listener.worldSizeUpdated();
        }
    }

    public void setBlocks(boolean[][] blocks) {
        this.blocks = blocks;
        for (WorldListener listener : listeners) {
            listener.blockUpdated();
        }
    }

    public void setBlockWidth(int blockWidth) {
        this.blockWidth = blockWidth;
        for (WorldListener listener : listeners) {
            listener.worldSizeUpdated();
        }
    }

    public void addListener(WorldListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(WorldListener listener) {
        this.listeners.remove(listener);
    }

    public boolean[][] getBlocks() {
        return blocks;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public Player getPlayer() {
        return player;
    }
}
