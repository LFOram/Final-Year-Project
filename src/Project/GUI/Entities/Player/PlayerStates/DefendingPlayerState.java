package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

public class DefendingPlayerState implements PlayerState {
    private Player player;
    private SkaterStats playerStats;

    public DefendingPlayerState(Player player) {
        this.player = player;
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

    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {

    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        System.out.println("Tick");
        moveTowardsPuck();
        //contactWithPuck();

    }

    @Override
    public void act() {

    }
}
