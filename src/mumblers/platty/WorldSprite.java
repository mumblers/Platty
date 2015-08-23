package mumblers.platty;

import mumblers.platty.engine.Drawable;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Tickable;
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
public class WorldSprite extends GameObject implements Tickable, WorldListener, Drawable {

    private static final Color BACKGROUND = new Color(100, 100, 255);

    private Player player;
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

    public static final int SPRITE_SIZE = 70;

    private static BufferedImage background;

    static {
        try {
            BufferedImage sheet = ImageIO.read(Platty.class.getResourceAsStream("wall.png"));
            for (int i = 0; i < images.length; i++) {
                images[i] = sheet.getSubimage((i % 2) * SPRITE_SIZE, (i / 2) * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
            }
            background = ImageIO.read(Platty.class.getResourceAsStream("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WorldSprite(World world, Player player) {
        this.world = world;
        this.player = player;
        this.world.addListener(this);
        worldSizeUpdated();
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

    @Override
    public void draw(Graphics2D g, int xScroll) {
        g.setColor(BACKGROUND);
        int width = (int) Platty.thiss.displaySize.getWidth();
        int height = (int) Platty.thiss.displaySize.getHeight();

        g.drawImage(background, 0, 0, width / 3, height, null);
        g.drawImage(background, width / 3, 0, width / 3, height, null);
        g.drawImage(background, (width - width / 3), 0, width / 3, height, null);

        for (int row = 0; row < blockImages.length; row++) {
            for (int col = 0; col < blockImages[0].length; col++) {
                int imgId = blockImages[row][col];
                int xx = col * SPRITE_SIZE - xScroll;
                int yy = row * SPRITE_SIZE;
                if (imgId != EMPTY_IMAGE_ID) {
                    g.drawImage(images[imgId], xx, yy, null);
                }
            }
        }
    }

    public int getPriority() {
        return 0;
    }
}