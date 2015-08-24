package mumblers.platty.entity;

import mumblers.platty.*;
import mumblers.platty.engine.Drawable;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Hitboxable;
import mumblers.platty.engine.Tickable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class PressurePlate extends GameObject implements Tickable, Drawable, Hitboxable {

    private static BufferedImage up, down;

    static {
        try {
            BufferedImage sheet = ImageIO.read(Platty.class.getResourceAsStream("launcher.png"));
            down = sheet.getSubimage(0, 0, 130, 70);
            up = sheet.getSubimage(0, 70, 130, 70);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Boss boss;

    private Rectangle hitbox;

    private Player thePlayer;

    private boolean pressed = false;
    private int count;

    public PressurePlate(Point point, Player thePlayer, Boss boss) {
        this.boss = boss;
        this.hitbox = new Rectangle(point, new Dimension(130, 70));
        this.thePlayer = thePlayer;
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void tick() {
        pressed = thePlayer.getHitbox().intersects(this.getHitbox()) && thePlayer.getMovement() == MovementStatus.DUCKING;
        if (pressed && count <= 0) {
            addRocket();
        }
        count--;
    }

    private void addRocket() {
        new Rocket(getHitbox().x + getHitbox().width / 2, getHitbox().y, boss);
        count = 120;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void draw(Graphics2D g, int xScroll) {
        g.drawImage(isPressed() ? down : up, getHitbox().x - xScroll, getHitbox().y, getHitbox().width, getHitbox().height, null);
    }

}
