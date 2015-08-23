package mumblers.platty;

import mumblers.platty.graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class PlayerSprite extends Sprite {

    private Player thePlayer;

    /**
     * STAND L
     * STAND R
     * WALK 1 L
     * WALK 1 R
     * WALK 2 L
     * WALK 2 R
     * DUCK L
     * DUCK R
     * HURT L
     * HURT R
     * JUMP L
     * JUMP R
     */
    private static BufferedImage[] images = null;

    private boolean walking2 = true;

    public PlayerSprite(Player thePlayer) {
        this.thePlayer = thePlayer;

        if (images == null) {
            images = new BufferedImage[12];
            for (int i = 0; i < 12; i += 2) {
                try {
                    images[i] = ImageIO.read(Platty.class.getResourceAsStream("player/alienBlue_" + (i / 2) + ".png"));
                    BufferedImage rotated = new BufferedImage(images[i].getWidth(), images[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
                    rotated.createGraphics().drawImage(images[i], rotated.getWidth(), rotated.getHeight(), 0, 0, null);
                    images[i + 1] = rotated;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getWidth() {
        return 76;
    }

    @Override
    public int getHeight() {
        return 96;
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {

    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale, int xOff, int yOff) {
        //
    }

    private int getPlayerBaseImage(MovementStatus movement) {
        switch (movement) {
            case DUCKING:
                return 3;
            case STANDING:
                return 0;
            case HURTING:
                return 4;
            case JUMPING:
                return 5;
            case WALKING:
                return walking2 ? 1 : 2;
        }
        return -1;
    }
}
