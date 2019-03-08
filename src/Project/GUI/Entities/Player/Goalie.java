package Project.GUI.Entities.Player;

import Project.Base.Enums.Position;
import Project.Base.Enums.Team;

import static java.lang.Math.*;

/**
 * Created by Leon on 02/12/2018.
 */
public class Goalie extends Player {

    private Position position = Position.GOALIE;
    private GoalieStats stats;

    public Goalie(PlayerDetails player, GoalieStats stats, Team team, Boolean home) {
        super(player,team,home);
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
        int speed = ((stats.statSkating * stats.statEndurance) / currentEndurance) / 10;
        float accel = stats.statSkating * 0.00625f; //0.00625 used to get ~0.5 average based on stats found from Leo Culhane, McGill University 2012
        float decel = 0.75f;

        float deltaX = targetX - x;
        float deltaY = targetY - y;
        float distance = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float decelDistance = (float) Math.pow(velocity,2) / (2 * decel);

        if (distance > decelDistance){//still accelerating if possible
            velocity = Math.min(velocity+accel,speed);
        }
        else {
            velocity = Math.max(velocity - decel, 0);
        }

        x += velocity * cos(angle);
        y += velocity * sin(angle);
    }

    public String toString(){
        String string = position.toString() + " - " +  super.toString();
        string += " SK:"+stats.getStatSkating()+" EN:"+stats.getStatEndurance()+" SI:"+stats.getStatSize()+" AG:"+stats.getStatAgility()+" RB:"+stats.getStatReboundControl();
        string += " SC"+stats.getStatStyleControl()+" HS:"+stats.getStatHandSpeed()+" PH:"+stats.getStatPuckHandling()+" PS:"+stats.getStatPenaltyShot();
        return string;
    }

    @Override
    public void tick() {
        if((int)x!= (int)targetX || (int)y!=(int)targetY){
            float direction = getDirection();
            move(direction);
        }
    }
}
