package mumblers.platty.entity;

import mumblers.platty.MovementStatus;
import mumblers.platty.Player;
import mumblers.platty.engine.Tickable;

import java.awt.*;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class PressurePlate implements Tickable {

    Rectangle hitbox;

    Player thePlayer;

    private boolean pressed = false;

    PressurePlateSprite sprite;

    public PressurePlate(Rectangle hitbox, Player thePlayer) {

        this.hitbox = hitbox;
        this.thePlayer = thePlayer;

        sprite = new PressurePlateSprite(this);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void tick() {
        pressed = thePlayer.getBounds().intersects(this.getHitbox()) && thePlayer.getMovement() == MovementStatus.DUCKING;
    }

    public boolean isPressed() {
        return pressed;
    }

    public PressurePlateSprite getSprite() {
        return sprite;
    }

    public void render(Graphics2D g, int currentCameraX) {
        sprite.render(g, hitbox.x - currentCameraX, hitbox.y);
        g.setColor(Color.red);
//        g.drawRect(hitbox.x - currentCameraX, hitbox.y, hitbox.width, hitbox.height);
//        g.drawRect(thePlayer.getBounds().x-currentCameraX, thePlayer.getBounds().y, thePlayer.getBounds().width, thePlayer.getBounds().height);
    }
}
