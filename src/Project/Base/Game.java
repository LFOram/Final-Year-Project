package Project.Base;

import Project.Base.Enums.Line;
import Project.Base.Enums.Possession;
import Project.Base.Enums.Team;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Entity;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Puck;

import java.util.*;
import java.sql.Connection;

import static java.lang.Math.sqrt;

/**
 * Created by Leon on 16/01/2019.
 */
public class Game {
    //Fields
    private TeamObject homeTeam;
    private TeamObject awayTeam;
    private Arena arena = Arena.getArena();
    private Puck puck;
    private int tickTimer = 0;
    private int homeScore = 0;
    private int awayScore = 0;
    private Random rand = new Random();


    public Game(Team home, Team away) {
        Assets.loadTeamAssets(home, away);
        puck = new Puck(this);
        homeTeam = new TeamObject(home, true);
        awayTeam = new TeamObject(away, false);
        setInitialPosition(homeTeam.getAllOnIce(), true);
        setInitialPosition(awayTeam.getAllOnIce(), false);
        for (Player player : homeTeam.getPlayerList().values()) {
            player.setGame(this);
        }
        for (Player player : awayTeam.getPlayerList().values()) {
            player.setGame(this);
        }

        puck.setSpeed(10);
    }

    public int random(int maxRandom){
        boolean useRandom = true;
        if(useRandom){
            return rand.nextInt(maxRandom+1);
        }
        else {
            return maxRandom;
        }
    }


    public static void setInitialPosition(HashMap<String, Player> team, Boolean home) {
        float[] position;
        float i = 0;
        for (Player player : team.values()) {
            position = Positions.getCenterFaceoff(player);
            player.setCurrentPosition(600 - (25 - (10 * i)), 100);
            player.setTargetPositionRelative(position[0], position[1], home);
        }
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> playerArrayList = new ArrayList<>();
        playerArrayList.addAll(homeTeam.getAllOnIce().values());
        playerArrayList.addAll(awayTeam.getAllOnIce().values());
        return playerArrayList;
    }

    public ArrayList<Player> getOpponentTeam(boolean isHome){
        ArrayList<Player> playerArrayList = new ArrayList<>();
        if(isHome){
            playerArrayList.addAll(getAwayTeam().getAllOnIce().values());
        }
        else {
            playerArrayList.addAll(getHomeTeam().getAllOnIce().values());
        }
        return playerArrayList;
    }

    public ArrayList<Player> getOwnTeam(boolean isHome){
        ArrayList<Player> playerArrayList = new ArrayList<>();
        if(isHome){
            playerArrayList.addAll(getHomeTeam().getAllOnIce().values());
        }
        else {
            playerArrayList.addAll(getAwayTeam().getAllOnIce().values());
        }
        return playerArrayList;
    }

    private boolean loopTeamPossession(TeamObject team){

        for (Player p:team.getAllOnIce().values()){
            if (p.getHasPuck()){
                return true;
            }
        }
        return false;
    }

    public boolean puckPossessed(){
        if(puck.getLastTouch() != Possession.FACEOFF){
            if (puck.getLastTouch() == Possession.HOME){
                return loopTeamPossession(homeTeam);
            }
            else {
                return loopTeamPossession(awayTeam);
            }
        }
        return false;
    }

    private float getDistanceToPuck(Player player){
        float deltaX = puck.getX() - player.getX();
        float deltaY = puck.getY() - player.getY();
        float distance = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return distance;
    }

    public boolean isClosestToPuck(Player player){
        float ownDistance = getDistanceToPuck(player);
        ArrayList<Player> playerlist = new ArrayList<>();
        if(player.isHomeTeam()){
            playerlist.addAll(homeTeam.getAllOnIce().values());
        }
        else {
            playerlist.addAll(awayTeam.getAllOnIce().values());
        }
        for (Player p:playerlist){
            if(p != player){
                if (getDistanceToPuck(p)> ownDistance){
                    return false;
                }
            }
        }
        return true;
    }

    public TeamObject getHomeTeam() {
        return homeTeam;
    }

    public TeamObject getAwayTeam() {
        return awayTeam;
    }

    public String listAllPlayers() {
        StringBuilder playerList = new StringBuilder("Home Team: " + homeTeam.getTeam().name() + "\n");
        Iterator<Map.Entry<String, Player>> it1 = homeTeam.getPlayerList().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, Player> pair = it1.next();
            playerList.append(pair.getValue().toString()).append("\n");
        }
        playerList.append("\nAway Team: ").append(awayTeam.getTeam().name()).append("\n");
        Iterator<Map.Entry<String, Player>> it2 = awayTeam.getPlayerList().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Player> pair = it2.next();
            playerList.append(pair.getValue().toString()).append("\n");
        }
        return playerList.toString();
    }

    public Puck getPuck() {
        return puck;
    }

    public Arena getArena() {
        return arena;
    }
}
