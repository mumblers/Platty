package mumblers.platty;

import mumblers.platty.world.World;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * todo
 *
 * @author davidot
 */
public class Bomb {

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

    public Rectangle boundingBox = new Rectangle(BOMB_SIZE);
    public boolean removeMe;

    public void render(Graphics2D g, int cameraX) {
        g.drawImage(++counter % 20 > 10 ? img : img2, boundingBox.x - cameraX, boundingBox.y, null);
    }

    public void tick(World world) {
        boundingBox.y += Player.FALL_SPEED;
        if (world.blockAtPixel(boundingBox.x, boundingBox.y + boundingBox.height)) {
            removeMe = true;
        }
        if (boundingBox.intersects(world.getPlayer().getBounds())) {
            Platty.thiss.resetGame();
        }
    }


}
