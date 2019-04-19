package Project.GUI.Entities;

import Project.Base.Enums.Position;
import Project.Base.Game;
import Project.GUI.Entities.Player.Player;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public abstract class Entity {
    protected Game game;

    protected float x,y;
    protected int width,height;
    protected float targetX,targetY;
    protected float xVelocity,yVelocity;
    protected Position currentPosition;
    protected Rectangle bounds;

    public Entity(float x,float y,int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int)x,(int) y,width,height);
    }

    public void setPositionAbsolute(int x,int y){
        this.x = x - width/2;
        this.y = y - height/2;
    }

    protected void updateBounds(float x, float y){
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int)(bounds.x+xOffset),(int)(bounds.y+yOffset));
    }

    public boolean checkPlayerCollision(float xOffset, float yOffset){
        for (Player p:game.getAllPlayers()){
            if(p.equals(this)){
                continue;
            }
//            System.out.println(p.getCollisionBounds(0f,0f));
//            System.out.println(getCollisionBounds(xOffset,yOffset));
//            if (p.getCollisionBounds(0f,0f).intersects(getCollisionBounds(xOffset,yOffset))){
//                return true;
//            }
            if(getDistance(this,p)<7){
                return true;
            }
            else return false;
        }
        return false;
    }

    private float getDistance(Entity a, Entity b){
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        return  (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public Player getHittingPlayer(Player player,ArrayList<Player> playerArrayList,float xOffset, float yOffset){
        for(Player p:playerArrayList){
            if(p.getCollisionBounds(0f,0f).intersects(getCollisionBounds(xOffset,yOffset))){
                return p;
            }
        }
        return null;
    }

    public boolean playerPuckCollision(Player player){
        if (player.getCollisionBounds(0f,0f).intersects(player.getGame().getPuck().getCollisionBounds(0f,0f))){
            return true;
        }
        else {
            return false;
        }
    }

    protected void move(){

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

    public void setTargetAbsolute(float x, float y, Boolean home){
        this.targetX = x;
        this.targetY = y;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
