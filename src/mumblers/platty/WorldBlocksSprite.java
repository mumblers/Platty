package mumblers.platty;

import mumblers.platty.graphics.Display;
import mumblers.platty.graphics.Sprite;
import mumblers.platty.graphics.Tickable;
import mumblers.platty.world.World;
import mumblers.platty.world.WorldListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Sinius on 19-8-2015.
 * World drawer
 */
public class WorldBlocksSprite extends Sprite implements Tickable, WorldListener {

    private static final Color BACKGROUND = new Color(100, 100, 255);
    /**
     * The world to draw
     */
    private World world;

    /**
     * All possible block images.
     */
    private static BufferedImage[] images = new BufferedImage[16];

    /**
     * The id inside the blockImages[][] a empty block has
     */
    private static final int EMPTY_IMAGE_ID = -1;

    /**
     * Here are all the blockImages saved. In the tick is calculated
     * what sprite is good for what block. this array saves those outcomes
     * for the renderer to render.
     */
    private int[][] blockImages;

    /**
     * Should do a tick to update the placements of the blocks
     */
    private boolean shouldDoTick = true;

    private int cameraX = 0;
    private int cameraY = 0;

    public static final int SPRITE_SIZE = 70;

    static {
        try {
            BufferedImage sheet = ImageIO.read(Platty.class.getResourceAsStream("wall.png"));
            for (int i = 0; i < images.length; i++) {
                images[i] = sheet.getSubimage((i % 2) * SPRITE_SIZE, (i / 2) * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WorldBlocksSprite(World world) {
        this.world = world;
        this.world.addListener(this);
        worldSizeUpdated();
    }

    @Override
    public int getWidth() {
        return Display.WIDTH;
    }

    @Override
    public int getHeight() {
        return Display.HEIGHT;
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(BACKGROUND);
        g.fillRect(x, y, width, height);
        updateScroll(width, height);
        for (int row = 0; row < blockImages.length; row++) {
            for (int col = 0; col < blockImages[0].length; col++) {
                int imgId = blockImages[row][col];
                int xx = col * SPRITE_SIZE - cameraX;
                int yy = row * SPRITE_SIZE - cameraY;
                if (imgId != EMPTY_IMAGE_ID) {
                    g.drawImage(images[imgId], xx, yy, null);
                }
            }
        }
    }

    private void updateScroll(int width, int height) {
        cameraX = width / 2;
        cameraX = Math.max(width / 2, cameraX);
    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale, int xOff, int yOff) {
        //nothing
    }

    @Override
    public void tick() {
        if (!shouldDoTick)
            return;
        for (int row = 0; row < world.getBlockHeight(); row++) {
            for (int col = 0; col < world.getBlockWidth(); col++) {
                if (!world.blockAt(row, col)) {
                    blockImages[row][col] = EMPTY_IMAGE_ID;
                } else {
                    blockImages[row][col] = calcImageId(world.blockAt(row, col - 1), world.blockAt(row, col + 1),
                            world.blockAt(row - 1, col), world.blockAt(row + 1, col));
                }

            }
        }
        shouldDoTick = false;
    }

    private static int calcImageId(boolean left, boolean right, boolean up, boolean down) {
        return (left ? 1 : 0) + (right ? 2 : 0) + (up ? 4 : 0) + (down ? 8 : 0);
    }

    @Override
    public void blockUpdated() {
        shouldDoTick = true;
    }

    @Override
    public void worldSizeUpdated() {
        blockImages = new int[world.getBlockHeight()][world.getBlockWidth()];

        for (int row = 0; row < world.getBlockHeight(); row++) {
            for (int col = 0; col < world.getBlockWidth(); col++) {
                blockImages[row][col] = EMPTY_IMAGE_ID;
            }
        }

        shouldDoTick = true;
    }
}