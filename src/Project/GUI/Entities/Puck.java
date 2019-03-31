package Project.GUI.Entities;

import Project.Base.Arena;
import Project.Base.Enums.Possession;
import Project.GUI.Assets.Assets;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import static java.lang.Math.*;

public class Puck extends Entity {
    private PropertyChangeSupport support;
    private Possession lastTouch;
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
    public void setLastTouch(Possession touch){
        support.firePropertyChange("lastTouch",this.lastTouch,touch);
        this.lastTouch = touch;
    }



    private void move(float angle) {
        int speed = 10;
        float accel = 2;
        float decel = 0.75f;

        float deltaX = targetX - x;
        float deltaY = targetY - y;
        float distance = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float decelDistance = (float) Math.pow(velocity,2) / (2 * decel);

        if (distance > decelDistance){//still accelerating if possible
            velocity = Math.min(velocity+accel,speed);
        }
        else {
            velocity = Math.max(velocity-decel,0);
        }
        x += velocity * cos(angle);
        y += velocity * sin(angle);
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
