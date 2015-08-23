package mumblers.platty.entity;

import mumblers.platty.Platty;
import mumblers.platty.graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Sinius15 on 23-8-2015.
 */
public class PressurePlateSprite extends Sprite {

    BufferedImage up, down;

    PressurePlate plate;

    public PressurePlateSprite(PressurePlate plate) {
        this.plate = plate;
        if (up == null) {
            try {
                BufferedImage sheet = ImageIO.read(Platty.class.getResourceAsStream("launcher.png"));
                down = sheet.getSubimage(0, 0, 130, 70);
                up = sheet.getSubimage(0, 70, 130, 70);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(plate.isPressed() ? down : up, x, y, width, height, null);
    }

    @Override
    public int getWidth() {
        return 130;
    }

    @Override
    public int getHeight() {
        return 70;
    }


}
