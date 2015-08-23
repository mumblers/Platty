package mumblers.platty;

import mumblers.platty.graphics.Input;
import mumblers.platty.graphics.Tickable;
import mumblers.platty.world.World;

import java.awt.*;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class Player implements Tickable {

    /**
     * For the player only RIGHT and LEFT
     */
    private Direction direction = Direction.RIGHT;

    /**
     * What movement is the player doing? This variable will give the answer
     */
    private MovementStatus movement = MovementStatus.STANDING;

    private Point location = new Point(0, 100);

    private Input input;

    private World world;

    private int walkCounter = 0;
    private int jumpCounter = 0;
    private boolean jumpLocked = false;

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

            if (jumpCounter < 100 && !jumpLocked)
                location.y -= 10;
            jumpCounter++;

        } else
            movement = MovementStatus.STANDING;


        if (input.right.isPressed() || input.left.isPressed()) {
            walkCounter++;
            if (walkCounter >= 5)
                movement = MovementStatus.WALKING1;
            else
                movement = MovementStatus.WALKING2;
            if (walkCounter >= 10)
                walkCounter = 0;

            if (input.right.isPressed()) {
                location.x += 7;
            } else {
                location.x -= 7;
            }
        }

        if (hasBlockBeneith()) {
            jumpCounter = 0;
            jumpLocked = false;
        } else {
            location.y += 5;
        }

        if (!input.up.isPressed() && jumpCounter > 0)
            jumpLocked = true;
    }

    private void updateLocation() {

    }

    public boolean hasBlockBeneith() {
        return world.blockAtPixel(location.x, location.y - 1) || world.blockAtPixel(location.x + 66, location.y - 1);
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
}