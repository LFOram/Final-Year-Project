package Project.GUI.Entities;

import Project.Base.Arena;
import Project.Base.Enums.Possession;
import Project.Base.Game;
import Project.GUI.Assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Console;
import java.util.Random;

import static java.lang.Math.*;

public class Puck extends Entity {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Possession lastTouch;
    private int direction = 1;
    private float angle;

    public BufferedImage puck = Assets.puck;
    Arena arena = Arena.getArena();
    Random rand = new Random();


    public Puck(Game game) {
        super(5, 5,5,5);
        this.game = game;
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

    public void setSpeed(int velocity) {
        this.xVelocity = velocity;
        this.yVelocity = velocity;
    }

    public void setAngeleCalculate(){
        var deltaX = targetX - x;
        var deltaY = targetY - y;
        var rad = Math.atan2(deltaY,deltaX);
        angle = (float) rad;
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    private void movePuck() {

        System.out.println(targetX + " " + targetY + "PUCK" + xVelocity);
        float decel = 0.005f;
        if (xVelocity > 0.1) {
            if (xVelocity >= 0) {
                xVelocity = xVelocity - decel;
            } else {
                xVelocity = xVelocity + decel;
            }
        }
        if (yVelocity > 0.1) {
            if (yVelocity < 0) {
                yVelocity = yVelocity - decel;
            } else {
                yVelocity = yVelocity + decel;
            }
        }

        float tx1 = (float) (x + xVelocity * cos(angle));
        float tx2 = (float) (x + bounds.width + xVelocity * cos(angle));
        float ty1 = (float) (y + yVelocity * sin(angle));
        float ty2 = (float) (y + bounds.height + yVelocity * sin(angle));
        if(game.getArena().legalMove(tx1,tx2,ty1,ty2)) {
            System.out.println(x + " " + y);
            System.out.println( xVelocity * cos(angle));
            System.out.println( yVelocity * sin(angle));
            x += xVelocity * cos(angle);
            y += yVelocity * sin(angle);
            System.out.println("MOVING");
        }
        else {
            int bounce = game.getArena().getBounce(x,y);
            if (bounce == 1){
                yVelocity = -yVelocity;
            }
            else if (bounce == 2){
                xVelocity = -xVelocity;
            }
            else {
                xVelocity = -xVelocity;
                yVelocity = -yVelocity;
            }

        }
    }

    public Possession getLastTouch(){
        return lastTouch;
    }

    public int getZone(){
        return arena.getThird(this);
    }

    public int getSpeed(){
        return Math.abs((int)xVelocity);
    }

    public float getVelocityX(){
        return xVelocity;
    }

    public float getVelocityY(){
        return yVelocity;
    }

    @Override
    public void tick() {
        movePuck();
        updateBounds(x,y);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(puck,(int) x,(int) y,null);
//        g.setColor(Color.red);
//        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);

    }
}
