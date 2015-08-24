package mumblers.platty;

import mumblers.platty.engine.Drawable;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Hitboxable;
import mumblers.platty.engine.Tickable;

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
public class Rocket extends GameObject implements Tickable, Drawable, Hitboxable {

    public static final Dimension ROCKET_SIZE = new Dimension(46, 104);
    private static BufferedImage img;
    private static BufferedImage img2;

    private long ticks = 0;
    private long start = System.currentTimeMillis();

    static {
        try {
            BufferedImage bufferedImage = ImageIO.read(Platty.class.getResourceAsStream("rocket.png"));
            img = bufferedImage.getSubimage(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight() / 2);
            img2 = bufferedImage.getSubimage(0, bufferedImage.getHeight() / 2, bufferedImage.getWidth(), bufferedImage.getHeight() / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int counter = 0;

    private Rectangle boundingBox = new Rectangle(ROCKET_SIZE);
    private Boss boss;

    public Rocket(int x, int y, Boss boss) {
        super();
        boundingBox.setLocation(x, y);
        this.boss = boss;
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
        boundingBox.y -= Player.FALL_SPEED * 2;
        if (boundingBox.y + boundingBox.height < 0) {
            removeMe();
        }
        if (boundingBox.intersects(boss.getHitbox())) {
//            Platty.thiss.resetGame();
            boss.onHit();
            removeMe();
        }
    }

}
