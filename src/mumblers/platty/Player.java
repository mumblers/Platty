package mumblers.platty;

import java.awt.*;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class Player {

    /**
     * For the player only RIGHT and LEFT
     */
    private Direction direction = Direction.RIGHT;

    /**
     * What movement is the player doing? This variable will give the answer
     */
    private MovementStatus movement = MovementStatus.STANDING;

    private Point location = new Point(0, 0);

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