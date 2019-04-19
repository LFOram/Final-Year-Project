package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

public class HasPuckPlayerState implements PlayerState {
    private Skater player;
    private SkaterStats playerStats;
    private boolean noAction = true;

    public HasPuckPlayerState(Skater player){
        this.player = player;
    }

    private void skateWithPuck(){
        player.getGame().getPuck().setSpeed(0);
        player.getGame().getPuck().setPositionAbsolute((int)player.getX()+5,(int)player.getY()+5);
    }

    private boolean inOffensiveZone(){
        if (player.getX()<400){
            return true;
        }
        else return false;
    }
    private void moveTowardsOffensiveZone(){
        if (player.isHomeTeam()){
            player.setTargetPositionRelative(350,player.getY(),false);
        }
        else {
            player.setTargetPositionRelative(350,player.getY(),true);
        }
    }

    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {
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

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        if (!inOffensiveZone()){
            moveTowardsOffensiveZone();
        }
    }

    @Override
    public void act() {
        if(noAction){
            skateWithPuck();
        }
    }
}
