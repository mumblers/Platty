package mumblers.platty;

import mumblers.platty.engine.*;
import mumblers.platty.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class Player extends GameObject implements Tickable, Hitboxable, Drawable {

    public static final Dimension SIZE = new Dimension(66, 92);

    private Direction direction = Direction.RIGHT;
    private MovementStatus movement = MovementStatus.STANDING;

    //the hitbox where the corner is left bottom!
    private Rectangle box = new Rectangle(new Point(100, 100), SIZE);

    private Input input;
    private World world;

    private int walkCounter = 0;
    private int jumpCounter = 0;
    private boolean jumpLocked = false;

    public static final int MOVEMENT_SPEED = 7;
    public static final int FALL_SPEED = 5;
    public static final int JUMP_SPEED = FALL_SPEED + 5;
    public static final int SWITCH_OTHER_WALKING = 5;
    public static final int JUMP_TIME = 30;

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

    static {
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

    public Player(Input input, World world) {
        this.input = input;
        this.world = world;
    }

    @Override
    public void tick() {
        if (input.left.isClicked())
            direction = Direction.LEFT;
        if (input.right.isClicked())
            direction = Direction.RIGHT;

        if (input.down.isPressed())
            movement = MovementStatus.DUCKING;
        else if (input.up.isPressed()) {
            movement = MovementStatus.JUMPING;

            if (jumpCounter < JUMP_TIME && !jumpLocked && !hasBlockAbove())
                box.y -= JUMP_SPEED;
            jumpCounter++;

        } else
            movement = MovementStatus.STANDING;


        if (input.right.isPressed() || input.left.isPressed()) {
            walkCounter++;
            if (walkCounter >= SWITCH_OTHER_WALKING)
                movement = MovementStatus.WALKING1;
            else
                movement = MovementStatus.WALKING2;
            if (walkCounter >= SWITCH_OTHER_WALKING * 2)
                walkCounter = 0;

            if (input.right.isPressed() && !hasBlockRight()) {
                box.x += MOVEMENT_SPEED;
            } else if (input.left.isPressed() && !hasBlockLeft()) {
                box.x -= MOVEMENT_SPEED;
            }
        }

        if (hasBlockBeneith()) {
            jumpCounter = 0;
            jumpLocked = false;
        } else {
            box.y += FALL_SPEED;
        }

        if (!input.up.isPressed() && jumpCounter > 0)
            jumpLocked = true;

        if (isOutOfBounds()) {
            Platty.thiss.resetGame();
        }
    }

    public int calculateXScroll(int width) {
        return Math.max(0, box.x - width / 2);
    }

    public boolean isOutOfBounds() {
        return box.getY() > world.getBlockHeight() * WorldSprite.SPRITE_SIZE + WorldSprite.SPRITE_SIZE;
    }

    public boolean hasBlockBeneith() {
        return world.blockAtPixel(box.x, box.y + 1) || world.blockAtPixel(box.x + 66, box.y + 1);
    }

    public boolean hasBlockAbove() {
        return world.blockAtPixel(box.x, box.y - 66) || world.blockAtPixel(box.x + 66, box.y - 66);
    }

    public boolean hasBlockLeft() {
        return world.blockAtPixel(box.x - 3, box.y - 1) || world.blockAtPixel(box.x - 3, box.y - 70);
    }

    public boolean hasBlockRight() {
        return world.blockAtPixel(box.x + 70, box.y - 1) || world.blockAtPixel(box.x + 66, box.y - 70);
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MovementStatus getMovement() {
        return movement;
    }

    public void setMovement(MovementStatus movement) {
        this.movement = movement;
    }

    @Override
    public Rectangle getHitbox() {
        Rectangle leftTopRect = new Rectangle(box);
        leftTopRect.setLocation(box.x, box.y - SIZE.height);
        return leftTopRect;
    }

    @Override
    public void draw(Graphics2D g, int xScroll) {
        g.drawImage(getPlayerImage(getMovement(), getDirection()), box.x - xScroll, box.y - SIZE.height, null);
    }

    private static BufferedImage getPlayerImage(MovementStatus movement, Direction direction) {
        return images[movement.ordinal() * 2 + (direction == Direction.LEFT ? 1 : 0)];
    }
}