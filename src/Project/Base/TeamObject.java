package Project.Base;

import Project.Base.Enums.Line;
import Project.Base.Enums.Position;
import Project.Base.Enums.Team;
import Project.GUI.Entities.Player.Goalie;
import Project.GUI.Entities.Player.Player;

import java.util.HashMap;

public class TeamObject {
    private final HashMap<String, Player> playerList;
    private HashMap<String, Player> currentFLine;
    private HashMap<String, Player> currentDLine;
    private Line fLine;
    private Line dLine;
    private HashMap<String, Player> currentGoalie;
    private final Boolean homeTeam;
    private final Team team;

    public TeamObject(Team team, Boolean homeTeam) {
        this.team = team;
        this.homeTeam = homeTeam;
        playerList = Database.loadTeam(team, homeTeam);
        setLines(Line.FORWARD1, Line.DEFENCE1);
        setCurrentGoalie(Line.GOALIE1);
    }


    private void loadlines() {
        currentFLine = Database.loadLines(playerList, team, fLine);
        currentDLine = Database.loadLines(playerList, team, dLine);
    }


    public HashMap<String, Player> getPlayerList() {
        return playerList;
    }

    public HashMap<String, Player> getCurrentFLine() {
        return currentFLine;
    }

    public HashMap<String, Player> getCurrentDLine() {
        return currentDLine;
    }

    public Player getCenter() {
        for (Player p : playerList.values()) {
            if (p.getCurrentPosition() == Position.CENTER) {
                return p;
            }
        }
        return null;
    }


        public HashMap<String, Player> getAllOnIce () {
            HashMap<String, Player> allmaps = new HashMap<>();
            allmaps.putAll(currentFLine);
            allmaps.putAll(currentDLine);
            allmaps.putAll(currentGoalie);
            return allmaps;
        }

        public Boolean getHomeTeam () {
            return homeTeam;
        }

        public Team getTeam () {
            return team;
        }

        public void setLines (Line fLine, Line dLine){
            this.fLine = fLine;
            this.dLine = dLine;
            loadlines();
        }

        public void setCurrentGoalie (Line line){
            currentGoalie = Database.loadLines(playerList, team, line);
        }
    }

