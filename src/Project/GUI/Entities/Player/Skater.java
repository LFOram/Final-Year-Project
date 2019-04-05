package Project.GUI.Entities.Player;

import Project.Base.Arena;
import Project.Base.Enums.Position;
import Project.Base.Enums.Possession;
import Project.Base.Enums.Team;
import Project.Base.Stats;
import Project.GUI.Entities.Player.PlayerStates.AttackingPlayerState;
import Project.GUI.Entities.Player.PlayerStates.DefendingPlayerState;
import Project.GUI.Entities.Player.PlayerStates.FaceoffPlayerState;
import Project.GUI.Entities.Player.PlayerStates.HasPuckPlayerState;

import java.awt.*;

import static java.lang.Math.*;

/**
 * Created by Leon on 02/12/2018.
 */
public class Skater extends Player {

    private Position position;
    private SkaterStats stats;

    private int hitTimer;

    //PlayerStates
    HasPuckPlayerState hasPuckPlayerState = new HasPuckPlayerState(this);
    AttackingPlayerState attackingPlayerState = new AttackingPlayerState(this);
    DefendingPlayerState defendingPlayerState = new DefendingPlayerState(this);
    FaceoffPlayerState faceoffPlayerState = new FaceoffPlayerState(this);



    public Skater(PlayerDetails player, Position position, SkaterStats stats, Team team,Boolean home) {
        super(player,team,home);
        this.position = position;
        this.stats = stats;

    }


    private void move(float angle) {
        //System.out.println("Direction:" + angle);
        int speed = ((stats.getStatSkating() * stats.getStatEndurance()) / currentEndurance) / 10;
        float accel = stats.getStatSkating() * 0.00625f; //0.00625 used to get ~0.5 average based on stats found from Leo Culhane, McGill University 2012
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

        float xMove = (float) (xVelocity * cos(angle));
        float yMove = (float) (yVelocity * sin(angle));
        float tx1 = (float) (x + xVelocity * cos(angle));
        float tx2 = (float) (x + bounds.width + xMove);
        float ty1 = (float) (y + yVelocity * sin(angle));
        float ty2 = (float) (y + bounds.height + yMove);
        if(getGame().getArena().legalMove(tx1,tx2,ty1,ty2)) {
            if(!checkPlayerCollision(xMove,0f)){
                moveX(xMove);
            }
            else {
                playerState.hit(xMove,0f,xMove,yMove);
            }
            if(!checkPlayerCollision(0f,yMove)){
                moveY(yMove);
            }
            else{
                playerState.hit(0f,yMove,xMove,yMove);
            }
        }
        else {
            xVelocity = 0;
            yVelocity = 0;
        }

    }

    public void moveX(float xMove){
        if(hitTimer<30) {
            x += xMove;
        }
    }

    public void moveY(float yMove){
        if(hitTimer<30) {
            y += yMove;
        }
    }

    public void beenHit(){
        xVelocity = 0;
        yVelocity = 0;
        resetHitTimer();
        setHasPuck(false);
    }

    public int getIdealOffenceZone(){
        switch (position){
            case CENTER:
                return 9;
            case LWING:
                return 4;
            case RWING:
                return 14;
            case LDEFENCE:
                return 2;
            case RDEFENCE:
                return 12;
        }
        return 0;
    }


    public SkaterStats getStats(){
        return stats;
    }

    public void resetHitTimer(){
        hitTimer = 0;
    }

    public int getHitTimer(){
        return hitTimer;
    }

    private void updateTimers(){
        hitTimer += 1;
    }

    public void setHasPuck(){
        if(isHomeTeam()){
            getGame().getPuck().setLastTouch(Possession.HOME);
        }
        else {
            getGame().getPuck().setLastTouch(Possession.AWAY);
        }
        setPlayerState(hasPuckPlayerState);
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
        updateTimers();
    }



}
