package mumblers.platty.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author davidot
 */
public interface DisplayRenderer {

    void render(Graphics2D g, Dimension size);

    void tick();
}