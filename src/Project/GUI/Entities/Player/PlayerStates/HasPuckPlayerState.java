package Project.GUI.Entities.Player.PlayerStates;

import Project.Base.Enums.Position;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HasPuckPlayerState implements PlayerState {
    private Skater player;
    private SkaterStats playerStats;
    private boolean noAction = true;
    private Integer[] zoneWeight;
    private boolean pass;
    private boolean shoot;
    Player passTo = null;

    public HasPuckPlayerState(Skater player){
        this.player = player;
        this.playerStats = player.getStats();
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
        if (!inOffensiveZone()) {
            moveTowardsOffensiveZone();
        }
        //get distance to goal
        int distanceToGoal = player.getGame().getGoalieDistance(player, player.isHomeTeam());

        //get distance to closest opponent
        int distanceToClosestPlayer = player.getGame().getClosestPlayerDistance(player);

        //get position of teammate
        ArrayList<Player> ownTeam = new ArrayList<>();
        ownTeam = player.getGame().getOwnTeam(player.isHomeTeam());
        Random rand = new Random();
        HashMap<Player, Integer> passablePlayers = new HashMap<>();
        for (Player p : ownTeam) {
            if (p.equals(player)) {
                continue;
            }
            if (p.getCurrentPosition() == Position.GOALIE) {
                continue;
            }
            passablePlayers.put(p, player.getGame().getDistance(player, p));
        }
        int noPassable = passablePlayers.size();
        int getPasable = rand.nextInt(noPassable);
        int i = 0;
        for (Player p:passablePlayers.keySet()){
            if(i == getPasable){
                passTo = p;
                break;
            }
            i++;
        }
        // check not passing to opponent
//        HashMap<Player,Integer> clearPasssablePlayers= new HashMap<>();
//        clearPasssablePlayers.putAll(passablePlayers);
//        for (Player p:clearPasssablePlayers.keySet()){
//    }

        //get clear shot to goal

        //get zone preference
        zoneWeight = player.getGame().getArena().getOffensiveZoneWeight(player.getGame().getArena().getZone(player.getX(),player.getY()));

        //move towards goal, pass, shoot

        int passChance;
        int shootChance;
        int skateChance;
        int PShAttributeDiff = Math.abs(playerStats.getStatPassing()-playerStats.getStatScoring());
        passChance = playerStats.getStatPassing()+zoneWeight[0]+player.getGame().random(100);
        shootChance = playerStats.getStatScoring()+zoneWeight[1]+player.getGame().random(50);
        skateChance = playerStats.getStatSkating()+zoneWeight[2]+player.getGame().random(250)+(distanceToGoal/10);
        if (skateChance< passChance || skateChance< shootChance){
            if (passChance>shootChance){
                //pass
                pass=true;
                shoot=false;
                noAction=false;
            }
            else {
                //shoot
                pass=false;
                shoot=true;
                noAction=false;
            }
        }
        else {
            //skate
            pass=false;
            shoot=false;
            noAction=true;

        }
        //skate


    }

    @Override
    public void act() {
        if(noAction){
            System.out.println("SKATING");
            skateWithPuck();
        }
        else if (pass){
            System.out.println("PASSING");
           player.pass(passTo);
        }
        else if (shoot){
            System.out.println("SHOOTING");
            player.shoot();
        }
    }
}
