package mumblers.platty;

import mumblers.platty.engine.Display;
import mumblers.platty.engine.DisplayImplementor;
import mumblers.platty.engine.GameObject;
import mumblers.platty.engine.Input;
import mumblers.platty.entity.PressurePlate;
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

    public Dimension displaySize;

    public static Platty thiss;

    private Player player;

    public Platty() {
        thiss = this;
        this.display = new Display("Platty");

        input = display.getInput();
        initGame();

        display.setRenderer(this);
        display.start();
    }

    private void initGame() {
        world = new World(display.getInput());
        worldSprite = new WorldSprite(world, player);

        player = new Player(display.getInput(), world);

        new Boss(new Point(world.getBlockWidth() * WorldSprite.SPRITE_SIZE - 300, 70), world, player);
        new PressurePlate(new Point(world.getBlockWidth() * WorldSprite.SPRITE_SIZE - 300, (world.getBlockHeight() - 2) * WorldSprite.SPRITE_SIZE), player);

    }

    @Override
    public void render(Graphics2D g, Dimension size) {
        displaySize = size;
        GameObject.drawGameObjects(g, player.calculateXScroll(display.getWidth()));
    }

    @Override
    public void tick() {
        GameObject.tickGameObjects();
    }


    public void resetGame() {
        System.exit(10);
        //todo: reset!
    }


    public static void main(String[] args) {
        new Platty();
    }

}
