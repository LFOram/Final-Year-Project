package Project.Base;

import Project.GUI.Entities.Player.Player;

import java.util.*;
import java.sql.Connection;

/**
 * Created by Leon on 16/01/2019.
 */
public class Game {
    //Fields
    Connection conn = null;

    private Team homeTeam;
    private Team awayTeam;

    private HashMap<String, Player> homePlayers = new HashMap<>();
    private HashMap<String,Player> awayPlayers = new HashMap<>();

    private HashMap<Line,ArrayList<Player>> homeLines = new HashMap<>();
    private HashMap<Line,ArrayList<Player>> awayLines = new HashMap<>();


    public Game(Team home, Team away){
        homeTeam = home;
        awayTeam = away;

        //load team
        //moved to Database
        //load lines
        homeLines = loadLines(homeTeam, homePlayers);
        awayLines = loadLines(awayTeam, awayPlayers);
    }


    private HashMap<Line,ArrayList<Player>> loadLines(Team load, HashMap<String, Player> playerList){
        HashMap<Line,ArrayList<Player>> lineList = new HashMap<>();
        return lineList;
    }

    public String listAllPlayers() {
        String playerList = "";
        playerList = "Home Team: " + homeTeam.name()+"\n";
        Iterator<Map.Entry<String, Player>> it1 = homePlayers.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, Player> pair = it1.next();
            playerList += pair.getValue().toString() +"\n";
        }
        playerList += "\nAway Team: " +awayTeam.name()+"\n";
        Iterator<Map.Entry<String, Player>> it2 = awayPlayers.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Player> pair = it2.next();
            playerList += pair.getValue().toString()+"\n";
        }
        return playerList;
    }
}