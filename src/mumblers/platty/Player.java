package mumblers.platty;

import mumblers.platty.graphics.Input;
import mumblers.platty.graphics.Tickable;
import mumblers.platty.world.World;

import java.awt.*;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class Player implements Tickable {

    public static final Dimension SIZE = new Dimension(66, 92);
    /**
     * For the player only RIGHT and LEFT
     */
    private Direction direction = Direction.RIGHT;

    /**
     * What movement is the player doing? This variable will give the answer
     */
    private MovementStatus movement = MovementStatus.STANDING;

    private Point location = new Point(6000, 100);

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
                location.y -= JUMP_SPEED;
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
                location.x += MOVEMENT_SPEED;
            } else if (input.left.isPressed() && !hasBlockLeft()) {
                location.x -= MOVEMENT_SPEED;
            }
        }

        if (hasBlockBeneith()) {
            jumpCounter = 0;
            jumpLocked = false;
        } else {
            location.y += FALL_SPEED;
        }

        if (!input.up.isPressed() && jumpCounter > 0)
            jumpLocked = true;

        if (isOutOfBounds()) {
            Platty.thiss.resetGame();
        }
    }

    public boolean isOutOfBounds() {
        return location.getY() > world.getBlockHeight() * WorldSprite.SPRITE_SIZE + WorldSprite.SPRITE_SIZE;

    }

    public boolean hasBlockBeneith() {
        return world.blockAtPixel(location.x, location.y + 1) || world.blockAtPixel(location.x + 66, location.y + 1);
    }

    public boolean hasBlockAbove() {
        return world.blockAtPixel(location.x, location.y - 66) || world.blockAtPixel(location.x + 66, location.y - 66);
    }

    public boolean hasBlockLeft() {
        return world.blockAtPixel(location.x - 3, location.y - 1) || world.blockAtPixel(location.x - 3, location.y - 70);
    }

    public boolean hasBlockRight() {
        return world.blockAtPixel(location.x + 70, location.y - 1) || world.blockAtPixel(location.x + 66, location.y - 70);
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

    public Point getLocation() {
        return location;
    }

    public Rectangle getBounds() {
        return new Rectangle(location.x, location.y - SIZE.height, SIZE.width, SIZE.height);
    }
}