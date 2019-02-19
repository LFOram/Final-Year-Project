package Project.States;

import Project.Base.Database;
import Project.Base.Team;
import Project.GUI.Assets;
import Project.GUI.Entities.Player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class SimState extends State {
    private HashMap<String, Player> playerList;
    private Player player1;
    private Player player2;

    public SimState(){
        Assets.init();
        Database.init();
        HashMap<String, Player> Team1 =  Database.loadTeam(Team.TOR,true);
        HashMap<String,Player> Team2 = Database.loadTeam(Team.SFP,false);
        player1 = Team1.get("Bob Bergen");
        player2 = Team2.get("Jonathan Lundberg");
        player2.setHomeTeam(false);
        player2.setX(700);
    }

    @Override
    public void update() {
        player1.tick();
        player2.tick();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);
        player1.render(g);
        player2.render(g);
        //g.drawImage(Assets.homeTeam,500,250,null);
        //g.drawImage(Assets.awayTeam,700,250,null);
    }
}
