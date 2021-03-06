package mumblers.platty;

import mumblers.platty.engine.Drawable;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Hitboxable;
import mumblers.platty.engine.Tickable;
import mumblers.platty.world.World;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author davidot
 */
public class Boss extends GameObject implements Tickable, Drawable, Hitboxable {

    private static final int SPEED = 7;
    public static final int FOLLOW_RANGE = Bomb.BOMB_SIZE.width / 2;

    private Rectangle boundingBox;
    private Direction direction = Direction.LEFT;
    private State state = State.HANGING;

    private World world;
    private Player player;

    private int flyCounter = 0;
    private int bombCount = 0;
    private boolean hit = false;
    private int hitTime = 0;

    private static BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(Platty.class.getResourceAsStream("bat/" + name + ".png"));
    }

    //
    public Boss(Point location, World world, Player player) {
        super();
        this.world = world;
        this.player = player;
        Dimension size = new Dimension(70, 47);
        boundingBox = new Rectangle(location, size);
    }

    @Override
    public void tick() {
        int playerX = player.getHitbox().x;
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
        if (state == State.DEAD) {
            if (!world.blockAtPixel(boundingBox.x, boundingBox.y + boundingBox.height)) {
                boundingBox.y += 4;
                boundingBox.x -= 4;
            }
            return;
        }
        if (state == State.HIT) {
            if (hitTime-- <= 0) {
                state = State.FLYING1;
            }
        }
        if (playerX > boundingBox.x) {
            //right
            direction = Direction.RIGHT;
            if (playerDistance < FOLLOW_RANGE) {
                if (bombCount <= 0) {
                    makeBomb();
                }
            } else {
                boundingBox.x += SPEED;
            }
        } else {
            direction = Direction.LEFT;
            if (playerDistance < 130) {
                if (bombCount <= 0) {
                    makeBomb();
                }
            } else {
                boundingBox.x -= SPEED;
            }
        }
        if (bombCount < -150) {
            makeBomb();
        }

        bombCount--;
    }

    private void makeBomb() {
        addBomb(boundingBox.x - boundingBox.width / 2, boundingBox.y + 10);
        bombCount = 70;
    }

    @Override
    public void draw(Graphics2D g, int xScroll) {
        int x = boundingBox.x - xScroll;
        int y = boundingBox.y;
        BufferedImage img = direction == Direction.RIGHT ? state.imgRight : state.imgLeft;
        g.drawImage(img, x, y, null);
    }

    @Override
    public Rectangle getHitbox() {
        return boundingBox;
    }

    public void addBomb(int x, int y) {
        Bomb bomb = new Bomb(world, player);
        bomb.getHitbox().x = x + Bomb.BOMB_SIZE.width / 2;
    }

    public void onHit() {
        if (hit) {
            state = State.DEAD;
        } else {
            state = State.HIT;
            hit = true;
            hitTime = 80;
        }
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
}
