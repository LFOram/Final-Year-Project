package Project.GUI.Entities.Player;

import Project.Base.Arena;
import Project.Base.Enums.Position;
import Project.Base.Enums.Possession;
import Project.Base.Enums.Team;
import Project.Base.Stats;
import Project.GUI.Entities.Player.PlayerStates.*;
import Project.States.FaceoffState;

import java.awt.*;

import static java.lang.Math.*;

/**
 * Created by Leon on 02/12/2018.
 */
public class Skater extends Player {

    private Position position;
    private SkaterStats stats;
    private int faceoffRoll = 0;

    private int hitTimer = 0;
    private int faceoffTimer = 0;
    private int speed;

    //PlayerStates
    DefaultPlayerState defaultPlayerState;
    HasPuckPlayerState hasPuckPlayerState;
    AttackingPlayerState attackingPlayerState;
    DefendingPlayerState defendingPlayerState;
    FaceoffPlayerState faceoffPlayerState;



    public Skater(PlayerDetails player, Position position, SkaterStats stats, Team team,Boolean home) {
        super(player,team,home);
        this.position = position;
        this.stats = stats;

        defaultPlayerState = new DefaultPlayerState(this);
        hasPuckPlayerState = new HasPuckPlayerState(this);
        attackingPlayerState = new AttackingPlayerState(this);
        defendingPlayerState = new DefendingPlayerState(this);
        faceoffPlayerState = new FaceoffPlayerState(this);

        this.setPlayerState(defaultPlayerState);


    }

    private void updateState(){
        if(hasPuck){
            this.setPlayerState(hasPuckPlayerState);
        }
        else {
            if (lastTouch == Possession.FACEOFF) {
                this.setPlayerState(faceoffPlayerState);
            } else if (lastTouch == Possession.HOME){
                if (homeTeam){
                    this.setPlayerState(attackingPlayerState);
                }
                else {
                    this.setPlayerState(defendingPlayerState);
                }
            } else if (lastTouch == Possession.AWAY) {
                if (homeTeam) {
                    this.setPlayerState(defendingPlayerState);
                } else {
                    this.setPlayerState(attackingPlayerState);
                }
            }
            else if (lastTouch == Possession.LOOSE){
                this.setPlayerState(defaultPlayerState);
            }

        }
    }


    public void move(float angle) {
        System.out.println("Direction:" + angle);
        speed = ((stats.getStatSkating() * stats.getStatEndurance()) / currentEndurance) / 10;
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
            if (!(playerState instanceof DefaultPlayerState)&& !(playerState instanceof FaceoffPlayerState)) {
                if(!checkPlayerCollision(xMove,0f)){
                    moveX(xMove);
                }
                else {
                    playerState.hit(xMove,0f,xMove,yMove);
                    System.out.println(toString() +" HITx");
                }
                if(!checkPlayerCollision(0f,yMove)){
                    moveY(yMove);
                }
                else{
                    playerState.hit(0f,yMove,xMove,yMove);
                    System.out.println(toString() + " HITy");
                }
            } else {
                //System.out.println("Default Move");
                moveX(xMove);
                moveY(yMove);
            }
        }
        else {
            System.out.println(toString() + " Not Legal");
            xVelocity = 0;
            yVelocity = 0;
        }

    }

    public int getSpeed() {
        return speed;
    }

    public void moveX(float xMove){
        if(hitTimer>30) {
            x += xMove;
        }
    }

    public void moveY(float yMove){
        if(hitTimer>30) {
            y += yMove;
        }
    }

    public void beenHit(){
        xVelocity = 0;
        yVelocity = 0;
        resetHitTimer();
        setHasPuck(false);
    }

    public void pass(Player target){
        float targetsX = target.getX();
        float targetsY = target.getY();
        int xnoise = getGame().random(125)-stats.getStatPassing();
        int ynoise = getGame().random(125)-stats.getStatPassing();
        if(getGame().random(1) == 0){
            xnoise = -xnoise;
        }
        if(getGame().random(1)== 1){
            ynoise = -ynoise;
        }
        float passTargetX = targetsX + xnoise;
        float passTergetY = targetsY + ynoise;

        int passStrength = 0;
        passStrength += stats.getStatStrength()*0.2;
        passStrength += stats.getStatPassing()*0.5;
        passStrength += game.random(50);
        passStrength = passStrength/10;
        System.out.println(passTargetX + " " + passTergetY + " " + passStrength);
        game.getPuck().setTargetAbsolute(passTargetX, passTergetY,isHomeTeam());
        game.getPuck().setAngeleCalculate();
        game.getPuck().setSpeed((passStrength));
    }

    public int getIdealOffenceZone(){
        switch (currentPosition){
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

    public int getFaceoffRoll() {
        System.out.println(toString() + " Returning faceoff roll of " + faceoffRoll);
        return faceoffRoll;
    }

    public void setFaceoffRoll(int faceoffRoll) {
        System.out.println(toString() + " Setting Faceoff Roll to " + faceoffRoll);
        this.faceoffRoll = faceoffRoll;
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
        hitTimer ++;
        faceoffTimer ++;
        System.out.println(hitTimer);
        System.out.println(faceoffTimer);
    }

    public void setHasPuck(){
        if(isHomeTeam()){
            getGame().getPuck().setLastTouch(Possession.HOME);
        }
        else {
            getGame().getPuck().setLastTouch(Possession.AWAY);
        }
        hasPuck = true;
        setPlayerState(hasPuckPlayerState);
    }

    public void faceoff(float x, float y){
        setPlayerState(faceoffPlayerState);
        setTargetPositionRelative(x,y,isHomeTeam());
    }

    public void faceoffGo(){
        faceoffPlayerState.think();
    }

    public int getFaceoffTimer(){
        return faceoffTimer;
    }

    public void resetFaceoffTimer(){
        faceoffTimer = 0;
    }

    public String toString() {
        String string = position.toString() + " - " +  super.toString();
        string += stats.toString();
        string += playerState.toString();
        string += "  ----  ";
        string += targetX;
        string += " ";
        string += targetY;
        return string;
    }

    public void tick() {
        updateState();
        System.out.println(toString());
        super.tick();
        //System.out.println(getPlayerName() +" x:"+ x +" y:" + y + " Tx:" + targetX + "Ty: "+targetY);
//        if((int)x!= (int)targetX || (int)y!=(int)targetY){
//            float direction = getDirection();
//            move(direction);
//        }
        //System.out.println(toString());
        move(getDirection());
        updateBounds(x,y);
        updateTimers();

    }



}
