package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Leon on 16/01/2019.
 */
public class Game {
    //Fields
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

    private HashMap<String,Player> loadTeam(Team load){
        HashMap<String,Player> playerList = new HashMap<>();
        String file = "Rosters.csv";

        //scanner to read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = null;
            Scanner scanner = null;
            Team[] teams = Team.values();

            while ((line = reader.readLine()) != null) {
                if (Arrays.asList(teams).contains(line)) { //Start new team
                    if (line.equals(load.name())) { //if team we want to load
                        String[] playerLine = line.split(",");
                        PlayerDetails playerDetails = new PlayerDetails(playerLine[0], playerLine[6], playerLine[8], playerLine[7], playerLine[2]);
                        //get position
                        if (playerLine[1].equals("16")){
                            //goalie
                            GoalieStats playerStats = new GoalieStats(playerLine[12],playerLine[13],playerLine[14],playerLine[15],playerLine[16],playerLine[17],playerLine[18],playerLine[19],playerLine[20]);
                            Player player = new Goalie(playerDetails,playerStats);
                            playerList.put(playerLine[0].toLowerCase(),player);
                        }
                        else{
                            SkaterStats playerStats = new SkaterStats(playerLine[12],playerLine[13],playerLine[14],playerLine[15],playerLine[16],playerLine[17],playerLine[18],playerLine[19],playerLine[20],playerLine[21],playerLine[22],playerLine[23]);
                            //get position
                            int rawposition = Integer.parseInt(playerLine[1]);
                            Position position = null;
                            switch (rawposition){
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
                            }

                            Player player = new Skater(playerDetails,position,playerStats);
                            playerList.put(playerLine[0].toLowerCase(),player);
                        }

                    }
                }
            }
        }
        catch (java.io.FileNotFoundException e){

        }
        catch (java.io.IOException e){

        }

        return playerList;
    }

    private HashMap<Line,ArrayList<Player>> loadLines(Team load, HashMap<String, Player> playerList){
        HashMap<Line,ArrayList<Player>> lineList = new HashMap<>();

        return lineList;
    }

   // public String getPlayers(Team team){

        //return teamList;
    }
}
