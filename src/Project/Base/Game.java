package Project.Base;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Leon on 16/01/2019.
 */
public class Game {
    //Fields
    Connection conn = null;

    private Team homeTeam;
    private Team awayTeam;

    private HashMap<String,Player> homePlayers = new HashMap<>();
    private HashMap<String,Player> awayPlayers = new HashMap<>();

    private HashMap<Line,ArrayList<Player>> homeLines = new HashMap<>();
    private HashMap<Line,ArrayList<Player>> awayLines = new HashMap<>();


    public Game(Team home, Team away){
        homeTeam = home;
        awayTeam = away;

        //load team
        homePlayers = loadTeam(homeTeam);
        awayPlayers = loadTeam(awayTeam);
        //load lines
        homeLines = loadLines(homeTeam, homePlayers);
        awayLines = loadLines(awayTeam, awayPlayers);
    }

    private HashMap<String,Player> loadTeam(Team load) {

        HashMap<String, Player> playerList = new HashMap<>();
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://127.0.0.1:3306/project";
            String username = "Java";
            String password = "";
            conn =DriverManager.getConnection(url,username,password);//jdbc:mysql://
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            //System.out.println("SQLState: " + ex.getSQLState());
            //System.out.println("VendorError: " + ex.getErrorCode());
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM Players WHERE Team = ?");
            stmt.setString(1,load.name());
            rs = stmt.executeQuery();

            while (rs.next()) {
                //To Player Details object
                String name = rs.getString("Name");
                String country = rs.getString("Country");
                String rookie = rs.getString("Rookie");
                int height = rs.getInt("Height");
                int weight = rs.getInt("Weight");

                PlayerDetails newPlayerDetails = new PlayerDetails(name, "22", height, weight, country);

                int rawposition = rs.getInt("Position");
                Position position = null;
                switch (rawposition) {
                    case 1:
                        position = Position.CENTER;
                        break;
                    case 2:
                        position = Position.LWING;
                        break;
                    case 4:
                        position = Position.RWING;
                        break;
                    case 8:
                        position = Position.DEFENCE;
                        break;
                    case 16:
                        position = Position.GOALIE;
                        break;
                }
                if (position == Position.GOALIE) {
                    //get goalie stats
                    int sk = rs.getInt("CK");
                    int en = rs.getInt("FG");
                    int si = rs.getInt("DI");
                    int ag = rs.getInt("SK");
                    int rc = rs.getInt("ST");
                    int sc = rs.getInt("EN");
                    int hs = rs.getInt("PH");
                    int ph = rs.getInt("FO");
                    int ps = rs.getInt("PA");
                    //create player
                    GoalieStats newStats = new GoalieStats(sk, en, si, ag, rc, sc, hs, ph, ps);
                    Player newPlayer = new Goalie(newPlayerDetails, newStats);
                    playerList.put(newPlayer.getPlayerName(), newPlayer);
                } else {
                    //ger skater stats
                    int ck = rs.getInt("CK");
                    int fg = rs.getInt("FG");
                    int di = rs.getInt("DI");
                    int sk = rs.getInt("SK");
                    int st = rs.getInt("ST");
                    int en = rs.getInt("EN");
                    int ph = rs.getInt("PH");
                    int fo = rs.getInt("FO");
                    int pa = rs.getInt("PA");
                    int sc = rs.getInt("SC");
                    int df = rs.getInt("DF");
                    int ps = rs.getInt("PS");
                    //create player
                    SkaterStats newStats = new SkaterStats(ck, fg, di, sk, st, en, ph, fo, pa, sc, df, ps);
                    Player newPlayer = new Skater(newPlayerDetails, position, newStats);
                    playerList.put(newPlayer.getPlayerName(), newPlayer);
                }
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return playerList;
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