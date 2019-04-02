package Project.GUI.Entities.Player;

import Project.Base.Arena;
import Project.Base.Enums.Position;
import Project.Base.Enums.Team;

import java.awt.*;

import static java.lang.Math.*;

/**
 * Created by Leon on 02/12/2018.
 */
public class Skater extends Player {

    private Position position;
    private SkaterStats stats;



    public Skater(PlayerDetails player, Position position, SkaterStats stats, Team team,Boolean home) {
        super(player,team,home);
        this.position = position;
        this.stats = stats;

    }


    private void move(float angle) {
        //System.out.println("Direction:" + angle);
        int speed = ((stats.statSkating * stats.statEndurance) / currentEndurance) / 10;
        float accel = stats.statSkating * 0.00625f; //0.00625 used to get ~0.5 average based on stats found from Leo Culhane, McGill University 2012
        float decel = 0.75f;

        float deltaX = targetX - x;
        float deltaY = targetY - y;
        float distance = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float decelDistance = (float) Math.pow(xVelocity,2) / (2 * decel);

        if (distance > decelDistance){//still accelerating if possible
            xVelocity = Math.min(xVelocity+accel,speed);
            yVelocity = Math.min(yVelocity+accel,speed);
        }
        else {
            xVelocity = Math.max(xVelocity - decel, 0);
            yVelocity = Math.max(yVelocity - decel, 0);
        }
        float tx1 = (float) (x + xVelocity * cos(angle));
        float tx2 = (float) (x + bounds.width + xVelocity * cos(angle));
        float ty1 = (float) (y + yVelocity * sin(angle));
        float ty2 = (float) (y + bounds.height + yVelocity * sin(angle));
        if(getGame().getArena().legalMove(tx1,tx2,ty1,ty2)) {
            x += xVelocity * cos(angle);
            y += yVelocity * sin(angle);
        }
        else {
            xVelocity = 0;
            yVelocity = 0;
        }

    }


    public String toString() {
        String string = position.toString() + " - " +  super.toString();
        string += stats.toString();
        return string;
    }

    public void tick() {
        //System.out.println(getPlayerName() +" x:"+ x +" y:" + y + " Tx:" + targetX + "Ty: "+targetY);
        if((int)x!= (int)targetX || (int)y!=(int)targetY){
            float direction = getDirection();
            move(direction);
        }
        updateBounds(x,y);
    }



}
