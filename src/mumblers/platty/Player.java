package mumblers.platty;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class Player {

    private Direction direction;

    private MovementStatus movement;

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
}