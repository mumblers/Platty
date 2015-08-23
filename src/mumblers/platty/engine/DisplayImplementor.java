package mumblers.platty.engine;

import java.awt.*;

/**
 * @author davidot
 */
public interface DisplayImplementor {

    void render(Graphics2D g, Dimension size);

    void tick();
}