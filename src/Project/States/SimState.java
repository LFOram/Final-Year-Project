package Project.States;

import Project.GUI.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SimState extends State {

    public SimState(){

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);
    }
}
