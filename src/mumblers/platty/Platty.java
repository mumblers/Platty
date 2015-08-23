package mumblers.platty;

import mumblers.platty.engine.Display;
import mumblers.platty.engine.DisplayImplementor;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Input;
import mumblers.platty.world.World;

import java.awt.*;

/**
 * Created by Sinius on 19-8-2015.
 */
public class Platty implements DisplayImplementor {

    Display display;
    WorldSprite worldSprite;
    World world;
    Input input;

    public static Platty thiss;

    private Player player;

    public Platty() {
        thiss = this;
        this.display = new Display("Platty");

        display.setRenderer(this);
        display.start();

        input = display.getInput();

        initGame();
    }

    private void initGame() {
        world = new World(display.getInput());
        worldSprite = new WorldSprite(world);

        player = new Player(display.getInput(), world);
    }

    @Override
    public void render(Graphics2D g, Dimension size) {
        GameObject.drawGameObjects(g, player.calculateXScroll(display.getWidth()));
    }

    @Override
    public void tick() {
        GameObject.tickGameObjects();
    }


    public void resetGame() {
        //todo: reset!
    }


    public static void main(String[] args) {
        new Platty();
    }

}
