package mumblers.platty.engine;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public abstract class GameObject {

    private static final ArrayList<GameObject> objects = new ArrayList<>();

    private static final ArrayList<GameObject> toRemove = new ArrayList<>();

    private static final ArrayList<GameObject> toAdd = new ArrayList<>();

    private static boolean isTicking = false;

    public GameObject() {
        addGameObject(this);
    }

    public static void tickGameObjects() {
        isTicking = true;

        for (GameObject obj : objects) {

            if (obj instanceof Tickable) {
                Tickable ticker = (Tickable) obj;
                ticker.tick();
            }
        }


        isTicking = false;

        objects.removeAll(toRemove);
        toRemove.clear();

        objects.addAll(toAdd);
        toAdd.clear();
    }

    public static void drawGameObjects(Graphics2D g, int xScroll) {
        for (int i = 0; i < 3; i++) {
            for (GameObject obj : objects) {
                if (obj.getPriority() != i)
                    continue;
                if (obj instanceof Drawable) {
                    Drawable drawer = (Drawable) obj;
                    drawer.draw(g, xScroll);
                }
            }
        }
    }

    public static void addGameObject(GameObject ob) {
        if (isTicking)
            toAdd.add(ob);
        else
            objects.add(ob);
    }

    public void removeMe() {
        toRemove.add(this);
    }

    public int getPriority() {
        return 1;
    }

    public static void reset() {

    }
}
