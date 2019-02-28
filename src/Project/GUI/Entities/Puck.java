package Project.GUI.Entities;

import Project.Base.Arena;
import Project.GUI.Assets.Assets;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class Puck extends Entity {
    private PropertyChangeSupport support;
    private int lastTouch;
    private int direction = 1;
    private int deltax = 6;
    private int deltay = 3;
    Arena arena = Arena.getArena();
    Random rand = new Random();

    public Puck() {
        super(607, 254);
    }


    //observer design pattern
    public void addPropertyChangeListener(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    }

    //broadcast change
    public void setLastTouch(int touch){
        support.firePropertyChange("lastTouch",this.lastTouch,touch);
        this.lastTouch = touch;
    }

    @Override
    public void tick() {
        if(!arena.onIce(x+3,y+3)){
            direction = -direction;
        }
        deltay = rand.nextInt(5);
        deltax = rand.nextInt(5);
        if (direction == 1) {
            x += deltax;
            y += deltay;
        }
        else{
            x -= deltax;
            y -= deltay;
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.puck,(int) x,(int) y,null);

    }
}
