package mumblers.platty;

import mumblers.platty.graphics.Display;
import mumblers.platty.graphics.DisplayRenderer;
import mumblers.platty.graphics.Tickable;
import mumblers.platty.world.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sinius on 19-8-2015.
 */
public class Platty implements DisplayRenderer {

    public ArrayList<Tickable> tickers = new ArrayList<>();

    Display display;
    WorldSprite worldSprite;
    World world;

    public boolean toReset;

    public static Platty thiss;

    public Platty() {
        thiss = this;
        this.display = new Display("Platty");

        display.setRenderer(this);
        display.start();

        resetGame();
    }

    @Override
    public void render(Graphics2D g, Dimension size) {
        if (worldSprite != null)
            worldSprite.render(g, 0, 0, display.getWidth(), display.getHeight());
    }

    @Override
    public void tick() {
        for (Tickable ticker : tickers) {
            ticker.tick();
        }
        if (toReset) {
            tickers.clear();

            world = new World(display.getInput());

            worldSprite = new WorldSprite(world);
            tickers.add(world.getPlayer());
            tickers.add(world.getBoss());
            tickers.add(worldSprite);
            tickers.add(display.getInput());
            toReset = false;
        }
    }

    public void resetGame() {
        toReset = true;
    }


    public static void main(String[] args) {
        new Platty();
    }

}
