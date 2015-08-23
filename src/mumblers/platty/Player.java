package mumblers.platty;

import mumblers.platty.graphics.Input;
import mumblers.platty.graphics.Tickable;

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

    private int walkCounter = 0;

    public Player(Input input) {
        this.input = input;
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

    @Override
    public void tick() {
        if (input.left.isClicked())
            direction = Direction.LEFT;
        if (input.right.isClicked())
            direction = Direction.RIGHT;

        if (input.down.isPressed())
            movement = MovementStatus.DUCKING;
        else if (input.up.isPressed())
            movement = MovementStatus.JUMPING;
        else if (input.right.isPressed() || input.left.isPressed()) {
            walkCounter++;
            if (walkCounter >= 5)
                movement = MovementStatus.WALKING1;
            else
                movement = MovementStatus.WALKING2;
            if (walkCounter >= 10)
                walkCounter = 0;
        } else
            movement = MovementStatus.STANDING;

    }
}