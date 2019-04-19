package Project.GUI.Entities.Player.PlayerStates;

import Project.Base.*;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

public class AttackingPlayerState implements PlayerState {
    private Skater player;
    private SkaterStats playerStats;
    private boolean canBeOffside;
    boolean closestToPuck = false;
    boolean goForPuck = false;
    boolean moveTowardsZone = true;
    int idealZone;

    public AttackingPlayerState(Skater player) {
        this.player = player;
        this.playerStats = player.getStats();
    }

    @Override
    public void hit(float xOffset, float yOffset,float xMove,float yMove){
        Player hittingPlayer = player.getHittingPlayer(player,player.getGame().getOpponentTeam(player.isHomeTeam()),xOffset,yOffset);
        //get own stat
        //combination of skating, strength, puck handling, checking, and randomness
        if (player.getHitTimer() < 30) {
            int ownStat = 0;
            ownStat += (playerStats.getStatSkating() * 0.35);
            ownStat += (playerStats.getStatStrength() * 0.45);
            ownStat += (playerStats.getStatPuckHandling() * 0.40);
            ownStat += (playerStats.getStatChecking() * 0.15);
            ownStat += (player.getCurrentEndurance() * 0.15);
            ownStat += (player.getGame().random(50));

            //get opponent stat
            SkaterStats hittingPlayerStats = (SkaterStats) hittingPlayer.getStats();
            int oppStat = 0;
            oppStat += (hittingPlayerStats.getStatSkating() * 0.35);
            oppStat += (hittingPlayerStats.getStatStrength() * 0.40);
            oppStat += (hittingPlayerStats.getStatPuckHandling() * 0.15);
            oppStat += (hittingPlayerStats.getStatChecking() * 0.45);
            oppStat += (hittingPlayer.getCurrentEndurance() * 0.15);
            oppStat += (hittingPlayer.getGame().random(50));

            //determine winner
            if (ownStat>oppStat){
                //keep puck
                player.moveX(xMove);
                player.moveY(yMove);
                player.resetHitTimer();
            }
            else {
                //lose puck
                player.beenHit();
            }
        }
        else {
            player.moveX(xMove);
            player.moveY(yMove);
        }
    }

    private float[] getIdealZonePossible(){
        float[] targetPosition;
        if(canBeOffside){
            //ensure won't move into offside position
            targetPosition = player.getGame().getArena().getZonePosition((((player.getIdealOffenceZone()-1) /5)));
        }
        else {
            System.out.println("ELSE");
            targetPosition = player.getGame().getArena().getZonePosition(player.getIdealOffenceZone());
        }
        return targetPosition;

    }

    private void moveTowardsIdealZone(){
        float[] targetPosition = getIdealZonePossible();
        System.out.println(targetPosition[1]);
        System.out.println(player.isHomeTeam());
        player.setTargetPositionRelative(targetPosition[0],targetPosition[1],!player.isHomeTeam());
    }

    private void moveTowardsPuck(){
        player.setTargetPositionAbsolute(player.getGame().getPuck().getX(),player.getGame().getPuck().getY());
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
                player.setHasPuck();
            }
        }
    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        //does someone have the puck
        boolean puckPossessed = player.getGame().puckPossessed();

        //how close to the puck are we
        if (!puckPossessed) {
            closestToPuck = player.getGame().isClosestToPuck(player);
            int goForPuckChance = 20;
            if (closestToPuck) {
                goForPuckChance += 50;
            }
            goForPuckChance += (player.getCurrentEndurance() / 10);
            goForPuckChance += (playerStats.getStatPuckHandling() * 0.1);
            if (goForPuckChance > player.getGame().random(100)) {
                goForPuck = true;
                moveTowardsZone = false;
            } else {
                goForPuck = false;
                moveTowardsZone = true;
            }
        }
        else {
            moveTowardsZone = true;
        }

        //is puck in offensive zone yet (offside?)
        if (player.isHomeTeam()) {
            if (player.getGame().getPuck().getZone() == 1) {
                //in offensive zone
                canBeOffside = false;
            } else {
                canBeOffside = true;
            }
        } else {
            if (player.getGame().getPuck().getZone() == 3) {
                //in offensive zone
                canBeOffside = false;
            } else {
                canBeOffside = true;
            }
        }

        //go for puck or move to better position

    }



    @Override
    public void act() {
        if(goForPuck){
            moveTowardsPuck();
        }
        else if (moveTowardsZone){
            moveTowardsIdealZone();
        }
        //contactWithPuck();
    }
}