package mumblers.platty.world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius on 19-8-2015.
 */
public class World {

    private List<WorldListener> listeners = new ArrayList<>();

    private boolean[][] blocks = new boolean[][]{
            {false, false, false, false, false, false, false, false},
            {false, true, true, true, false, true, false, false},
            {false, true, true, true, false, false, false, false},
            {false, true, false, true, false, false, true, false},
            {false, false, false, false, false, false, true, false},
            {false, false, true, false, false, false, true, false},
            {false, true, true, true, false, false, false, false},
            {false, false, true, false, false, true, true, true}
    };

    //these vars can not changes because than the WorldBlockSprite should also update
    private int blockWidth = blocks[0].length;
    private int blockHeight = blocks.length;

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
}
