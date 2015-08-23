package mumblers.platty;

import mumblers.platty.engine.Drawable;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Hitboxable;
import mumblers.platty.engine.Tickable;
import mumblers.platty.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author davidot
 */
public class Bomb extends GameObject implements Drawable, Tickable, Hitboxable {

    public static final Dimension BOMB_SIZE = new Dimension(104, 116);
    private static BufferedImage img;
    private static BufferedImage img2;

    static {
        try {
            img = ImageIO.read(Platty.class.getResourceAsStream("bat/bomb.png"));
            img2 = ImageIO.read(Platty.class.getResourceAsStream("bat/bomb2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int counter = 0;

    private Rectangle boundingBox = new Rectangle(BOMB_SIZE);
    private World world;
    private Player player;

    public Bomb(World world, Player player) {
        super();
        this.world = world;
        this.player = player;
    }

    @Override
    public void draw(Graphics2D g, int xScroll) {
        g.drawImage(++counter % 20 > 10 ? img : img2, boundingBox.x - xScroll, boundingBox.y, null);
    }

    @Override
    public Rectangle getHitbox() {
        return boundingBox;
    }

    @Override
    public void tick() {
        boundingBox.y += Player.FALL_SPEED;
        if (world.blockAtPixel(boundingBox.x, boundingBox.y + boundingBox.height)) {
            removeMe();
        }
        if (boundingBox.intersects(player.getHitbox())) {
            Platty.thiss.resetGame();
        }
    }


}
