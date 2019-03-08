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

    private float getDirection(){
        var deltaX = targetX - x;
        var deltaY = targetY - y;
        var rad = Math.atan2(deltaY,deltaX);
        //var deg = rad * (180/Math.PI);
        return (float) rad;
    }


    private void move(float angle) {
        //System.out.println("Direction:" + angle);
        int speed = ((stats.statSkating * stats.statEndurance) / currentEndurance) / 10;
        float accel = stats.statSkating * 0.00625f; //0.00625 used to get ~0.5 average based on stats found from Leo Culhane, McGill University 2012
        float decel = 0.75f;

        float deltaX = targetX - x;
        float deltaY = targetY - y;
        float distance = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float decelDistance = (float) Math.pow(velocity,2) / (2 * decel);

        //System.out.println("Distance:" +distance);

        if (distance > decelDistance){//still accelerating if possible
            velocity = Math.min(velocity+accel,speed);
        }
        else {
            //System.out.println("Decelerating");
            velocity = Math.max(velocity-decel,0);
        }

        //System.out.println(velocity);
        //System.out.println(cos(angle));
        //System.out.println(velocity* cos(angle) + " " +velocity * sin(angle));

        x += velocity * cos(angle);
        y += velocity * sin(angle);
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
    }



}
