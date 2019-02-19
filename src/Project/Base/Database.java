package Project.Base;

import Project.GUI.Entities.Player.*;

import java.sql.*;
import java.util.HashMap;

public class Database {
    private static Connection conn = null;

    public static void init() {

        //setup database connection
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/project";
            String username = "Java";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);//jdbc:mysql://
            System.out.println("Connected");
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    private static ResultSet executeSQL(PreparedStatement stmt){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    public static int[] getTeamPosition(Team team){
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT XPos,YPos FROM Teams WHERE Team = ?");
            System.out.println(team.name());
            ps.setString(1,team.name());
            ResultSet rs = executeSQL(ps);
            int xPos = 0;
            int yPos = 0;
            while (rs.next()) {
                xPos = rs.getInt("XPos")-1;
                yPos = rs.getInt("YPos")-1;
            }
            return new int[]{xPos,yPos};

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static HashMap<String, Player> loadTeam(Team load,boolean home){
        HashMap<String, Player> playerList = new HashMap<>();

        try {
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM Players WHERE Team = ?");
            pstm.setString(1,load.name());
            ResultSet rs = executeSQL(pstm);

            while (rs.next()) {
                //To Player Details object
                String name = rs.getString("Name");
                String number = rs.getString("Number");
                Team team = Team.valueOf(rs.getString("Team"));
                String country = rs.getString("Country");
                String rookie = rs.getString("Rookie");
                int height = rs.getInt("Height");
                int weight = rs.getInt("Weight");

                PlayerDetails newPlayerDetails = new PlayerDetails(name, number, height, weight, country);

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
                    Player newPlayer = new Goalie(newPlayerDetails, newStats,team,home);
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
                    Player newPlayer = new Skater(newPlayerDetails, position, newStats,team,home);
                    playerList.put(newPlayer.getPlayerName(), newPlayer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public static int getTeamNumber(Team team){
        try {
            PreparedStatement pstm = conn.prepareStatement("SELECT TeamNo FROM Teams WHERE Team = ?");
            pstm.setString(1,team.name());
            ResultSet rs = executeSQL(pstm);
            while (rs.next()){
                return rs.getInt("TeamNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}


