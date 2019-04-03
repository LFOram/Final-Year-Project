package Project.Base;

import Project.Base.Enums.Line;
import Project.Base.Enums.Team;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Puck;

import java.util.*;
import java.sql.Connection;

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


    public static void setInitialPosition(HashMap<String, Player> team, Boolean home) {
        float[] position;
        float i = 0;
        for (Player player : team.values()) {
            position = Positions.getCenterFaceoff(player);
            player.setCurrentPosition(600 - (25 - (10 * i)), 100);
            player.setTargetPositionRelative(position[0], position[1], home);
        }
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
