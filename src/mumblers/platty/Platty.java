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

    ArrayList<Tickable> tickers = new ArrayList<>();

    Display display;
    WorldBlocksSprite worldSprite;
    World world;

    Player player;
    PlayerSprite playerSprite;

    public Platty() {
        this.display = new Display("Platty");


        display.setRenderer(this);
        world = new World();

        player = new Player(display.getInput());
        playerSprite = new PlayerSprite(player);

        worldSprite = new WorldBlocksSprite(world, display.getInput());
        tickers.add(worldSprite);
        tickers.add(player);
        tickers.add(display.getInput());


        display.start();


    }

    public static void main(String[] args) {
        new Platty();
    }

    @Override
    public void render(Graphics2D g, Dimension size) {
        worldSprite.render(g, 0, 0, display.getWidth(), display.getHeight());
        playerSprite.render(g, player.getLocation().x, player.getLocation().y);
    }

    @Override
    public void tick() {
        for (Tickable ticker : tickers) {
            ticker.tick();
        }
    }
}
