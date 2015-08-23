package mumblers.platty;

import mumblers.platty.graphics.Sprite;
import mumblers.platty.graphics.Tickable;
import mumblers.platty.world.World;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * todo
 *
 * @author davidot
 */
public class Boss extends Sprite implements Tickable {

    private static final int SPEED = 7;
    private Dimension size = new Dimension(70, 47);
    private Rectangle boundingBox;


    private Direction direction = Direction.LEFT;

    private State state = State.HANGING;
    private World world;
    private int flyCounter = 0;
    private int bombCount = 0;

    private static BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(Platty.class.getResourceAsStream("bat/" + name + ".png"));
    }

    public Boss(World world) {
        this.world = world;
        boundingBox = new Rectangle(new Point(world.getBlockWidth() * WorldSprite.SPRITE_SIZE - 300, 70), size);
    }

    @Override
    public void tick() {
        int playerX = world.getPlayer().getLocation().x;
        int playerDistance = Math.abs(boundingBox.x - playerX);
        if (state == State.HANGING) {
            if (playerDistance < 300) {
                state = State.FLYING1;
                System.out.println("MR BAT WOKE UP");
            }
            return;
        }
        if (state == State.FLYING1 || state == State.FLYING2) {
            flyCounter++;
            if (flyCounter % 8 == 0) {
                state = state == State.FLYING1 ? State.FLYING2 : State.FLYING1;
            }
        }
        if (playerX > boundingBox.x) {
            //right
            direction = Direction.RIGHT;
            if (playerDistance < 130) {
                if (bombCount <= 0) {
                    world.addBomb(boundingBox.x, boundingBox.y + 10);
                    bombCount = 50;
                }
            } else {
                boundingBox.x += SPEED;
            }
        } else {
            direction = Direction.LEFT;
            if (playerDistance < 130) {
                if (bombCount <= 0) {
                    world.addBomb(boundingBox.x, boundingBox.y + 10);
                    bombCount = 50;
                }
            } else {
                boundingBox.x -= SPEED;
            }
        }

        bombCount--;
    }

    public void render(Graphics2D g, int cameraX) {
        render(g, boundingBox.x - cameraX, boundingBox.y);
    }

    private enum State {
        FLYING1("bat"),
        FLYING2("fly"),
        HIT("hit"),
        DEAD("dead"),
        HANGING("hang");

        final String spriteName;
        final BufferedImage imgLeft;
        final BufferedImage imgRight;

        State(String spriteName) {
            this.spriteName = spriteName;
            try {
                imgLeft = loadImage(spriteName);
                imgRight = new BufferedImage(imgLeft.getWidth(), imgLeft.getHeight(), BufferedImage.TYPE_INT_ARGB);
                imgRight.createGraphics().drawImage(imgLeft, imgLeft.getWidth(), 0, -imgLeft.getWidth(), imgLeft.getHeight(), null);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    @Override
    public int getWidth() {
        return boundingBox.width;
    }

    @Override
    public int getHeight() {
        return boundingBox.height;
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        BufferedImage img = direction == Direction.RIGHT ? state.imgRight : state.imgLeft;
        g.drawImage(img, x - img.getWidth(), y - img.getHeight(), null);
    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale, int xOff, int yOff) {
        //dont you fuck
    }
}
