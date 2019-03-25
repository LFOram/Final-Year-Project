package Project.Base;

import Project.Base.Enums.Line;
import Project.Base.Enums.Team;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;

import java.util.*;
import java.sql.Connection;

/**
 * Created by Leon on 16/01/2019.
 */
public class Game {
    //Fields
    private TeamObject homeTeam;
    private TeamObject awayTeam;



    public Game(Team home, Team away){
        new Assets();
        Assets.loadTeamAssets(home,away);
        homeTeam = new TeamObject(home,true);
        awayTeam = new TeamObject(away, false);
        setInitialPosition(homeTeam.getAllOnIce(),true);
        setInitialPosition(awayTeam.getAllOnIce(),false);

    }

    public static void setInitialPosition(HashMap<String,Player> team,Boolean home) {
        float[] position = new float[2];
        float i = 0;
        for(Player player: team.values()){
            position = Positions.getCenterFaceoff(player);
            player.setCurrentPosition(600-(25-(10*i)),40);
            player.setTargetPositionRelative(position[0],position[1],home);
        }
    }

    public TeamObject getHomeTeam() {
        return homeTeam;
    }

    public TeamObject getAwayTeam() {
        return awayTeam;
    }

    public String listAllPlayers() {
        String playerList = "";
        playerList = "Home Team: " + homeTeam.getTeam().name()+"\n";
        Iterator<Map.Entry<String, Player>> it1 = homeTeam.getPlayerList().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, Player> pair = it1.next();
            playerList += pair.getValue().toString() +"\n";
        }
        playerList += "\nAway Team: " +awayTeam.getTeam().name()+"\n";
        Iterator<Map.Entry<String, Player>> it2 = awayTeam.getPlayerList().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Player> pair = it2.next();
            playerList += pair.getValue().toString()+"\n";
        }
        return playerList;
    }
}