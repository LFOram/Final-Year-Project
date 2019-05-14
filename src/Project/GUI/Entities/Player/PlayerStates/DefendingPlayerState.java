package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

import static java.lang.Math.sqrt;

public class DefendingPlayerState implements PlayerState {
    private Skater player;
    private SkaterStats playerStats;

    public DefendingPlayerState(Player player) {
        this.player = (Skater)player;
        this.playerStats = (SkaterStats)player.getStats();

    }

    private void contactWithPuck(){
        if(player.playerPuckCollision(player)){
            //collision with puck. Does player collect puck
            //puck handling, skating, randomness, puck speed
            int puckCollectionChance = 0;
            puckCollectionChance += playerStats.getStatPuckHandling()*0.5; //50% PH
            puckCollectionChance += playerStats.getStatSkating() *0.25; //20% SK
            int puckSpeed = player.getGame().getPuck().getSpeed();
            //check if collect puck
            if (puckCollectionChance > player.getGame().random(100+puckSpeed*5)){
                //collects puck
                ((Skater)player).setHasPuck();
            }
        }
    }

    private void moveTowardsPuck(){
        player.setTargetPositionAbsolute(player.getGame().getPuck().getX(),player.getGame().getPuck().getY());
    }

    private void moveTowardsIdealZone(){
        float[] targetPosition;
        targetPosition = player.getGame().getArena().getZonePosition(player.getIdealDefenceZone());
        player.setTargetPositionRelative(targetPosition[0],targetPosition[1],player.isHomeTeam());
    }

    private float distanceToPuck(){
        float puckX = player.getGame().getPuck().getX();
        float puckY = player.getGame().getPuck().getY();

        float deltaX = puckX - player.getX();
        float deltaY = puckY - player.getY();
        float distanceToPuck = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return distanceToPuck;
    }

    private void pickUpPuck(){
        if (distanceToPuck()<15&&player.getGame().getPuck().getPuckTimer()>10){
            System.out.println("GETTING PUCK");
            System.out.println(distanceToPuck());
            //pick up puck
            (player).setHasPuck();
        }
    }

    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {

    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        System.out.println("Tick");
        //contactWithPuck();
        pickUpPuck();

    }

    @Override
    public void act() {
        moveTowardsIdealZone();
    }
}
