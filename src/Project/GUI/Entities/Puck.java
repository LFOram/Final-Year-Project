package Project.GUI.Entities;

import Project.GUI.Assets;

import java.awt.*;

public class Puck extends Entity {
    public Puck() {
        super(607, 254);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.puck,(int) x,(int) y,null);

    }
}
