package mumblers.platty.engine;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public abstract class GameObject {

    private static final ArrayList<GameObject> objects = new ArrayList<>();

    private static final ArrayList<GameObject> toRemove = new ArrayList<>();

    public GameObject() {
        addGameObject(this);
    }

    public static void tickGameObjects() {
        for (GameObject obj : objects) {
            if (obj instanceof Tickable) {
                Tickable ticker = (Tickable) obj;
                ticker.tick();
            }
        }
        objects.removeAll(toRemove);
    }

    public static void drawGameObjects(Graphics2D g, int xScroll) {
        for (GameObject obj : objects) {
            if (obj instanceof Drawable) {
                Drawable drawer = (Drawable) obj;
                drawer.draw(g, xScroll);
            }
        }
    }

    public static void addGameObject(GameObject ob) {
        objects.add(ob);
    }

    public void removeMe() {
        toRemove.add(this);
    }

    public static void reset() {

    }
}
