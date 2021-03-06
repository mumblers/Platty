package mumblers.platty.world;

import mumblers.platty.Boss;
import mumblers.platty.Player;
import mumblers.platty.WorldSprite;
import mumblers.platty.engine.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius on 19-8-2015.
 */
public class World {

    private Boss boss;
    private Player player;
    private List<WorldListener> listeners = new ArrayList<>();

    public static String[] defaultWorld = {
            "10000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000000000000000000000000001010000010000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000000000000000000000000001010111110000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000000000100000000000000011010001110000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000000000100000000000001001011000110000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000001000100000000001000011010001110000000000000000000000000000000000000000000000000000000000000000000001",
            "10000000001000100000000000001001010100010000000000000000000000000000000000000000000000000000000000000000000001",
            "10000010000000100000000001000001011111010000000000000000000000000000000000000000000000000000000000000000000001",
            "10000011000000100000010000000001000000010000000000000000000000000000000000000000000000000000000000000000000001",
            "10011111111111111111111111111111111111110000000000000000111011101101101101010010010010001000111111111111111111",
    };

    private boolean[][] blocks;

    //these vars can not changes because than the WorldBlockSprite should also update
    private int blockWidth;// = blocks[0].length;
    private int blockHeight;// = blocks.length;
    private static final char WALL_TILE = '1';


    public World(Input input) {
        blockWidth = defaultWorld[0].length();
        blockHeight = defaultWorld.length;
        blocks = new boolean[blockHeight][blockWidth];
        makeBlocksFromString(defaultWorld);
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

    public boolean blockAtPixel(int x, int y) {
        int row = y / WorldSprite.SPRITE_SIZE;
        int col = x / WorldSprite.SPRITE_SIZE;
        return blockAt(row, col);
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
}
