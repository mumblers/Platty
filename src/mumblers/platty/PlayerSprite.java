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

    public PlayerSprite(Player thePlayer) {
        this.thePlayer = thePlayer;

        if (images == null) {
            System.out.println("Loading player images");
            images = new BufferedImage[12];
            for (int i = 0; i < 12; i += 2) {
                try {
                    images[i] = ImageIO.read(Platty.class.getResourceAsStream("player/alienBlue_" + (i / 2) + ".png"));
                    BufferedImage rotated = new BufferedImage(images[i].getWidth(), images[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
                    rotated.createGraphics().drawImage(images[i], images[i].getWidth(), 0, -images[i].getWidth(), images[i].getHeight(), null);
                    images[i + 1] = rotated;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getWidth() {
        return getPlayerImage(thePlayer.getMovement(), thePlayer.getDirection()).getWidth();
    }

    @Override
    public int getHeight() {
        return getPlayerImage(thePlayer.getMovement(), thePlayer.getDirection()).getHeight();
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(getPlayerImage(thePlayer.getMovement(), thePlayer.getDirection()), x, y, null);
    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale, int xOff, int yOff) {
        //
    }

    private static BufferedImage getPlayerImage(MovementStatus movement, Direction direction) {
        return images[movement.ordinal() * 2 + (direction == Direction.LEFT ? 1 : 0)];
    }
}
