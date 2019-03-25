package Project.GUI.Entities;

import Project.Base.Enums.Position;

import java.awt.*;

public abstract class Entity {
    protected float x,y;
    protected float targetX,targetY;
    protected float velocity;
    protected Position currentPosition;

    public Entity(float x,float y){
        this.x = x;
        this.y = y;
    }

    public void setTargetPositionRelative(float x, float y, Boolean home){
        if(home){
            this.targetX = x+595;
            this.targetY = y;
        }
        else {
            this.targetX = 595 - x;
            this.targetY = y;
        }
    }

    public void setPositionRelative(float x, float y, Boolean home){
        if(home){
            this.x = x+595;
            this.y = y;
        }
        else {
            this.x = 595 - x;
            this.y = y;
        }
    }


    public abstract void tick();

    public abstract void render(Graphics g);
}
