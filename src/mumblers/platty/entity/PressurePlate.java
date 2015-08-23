package mumblers.platty.entity;

import mumblers.platty.MovementStatus;
import mumblers.platty.Platty;
import mumblers.platty.Player;
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

    private Rectangle hitbox;

    private Player thePlayer;

    private boolean pressed = false;

    //plate = new PressurePlate(new Rectangle(new Point(getBlockWidth() * WorldSprite.SPRITE_SIZE - 300, (getBlockHeight() - 2) * WorldSprite.SPRITE_SIZE), new Dimension(130, 70)), player);
    public PressurePlate(Point point, Player thePlayer) {
        super();

        this.hitbox = new Rectangle(point, new Dimension(130, 70));
        this.thePlayer = thePlayer;

        if (up == null) {
            try {
                BufferedImage sheet = ImageIO.read(Platty.class.getResourceAsStream("launcher.png"));
                down = sheet.getSubimage(0, 0, 130, 70);
                up = sheet.getSubimage(0, 70, 130, 70);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void tick() {
        pressed = thePlayer.getHitbox().intersects(this.getHitbox()) && thePlayer.getMovement() == MovementStatus.DUCKING;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void draw(Graphics2D g, int xScroll) {
        g.drawImage(isPressed() ? down : up, getHitbox().x, getHitbox().y, getHitbox().width, getHitbox().height, null);
    }


}
