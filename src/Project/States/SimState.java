package Project.States;

import Project.Base.Arena;
import Project.Base.Database;
import Project.Base.Team;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Puck;

import java.awt.*;
import java.util.HashMap;


public class SimState extends State {
    private HashMap<String, Player> playerList;
    private Player player1;
    private Player player2;
    private Puck puck;
    private Arena a;

    public SimState(){
        Assets.init();
        Database.init();
        HashMap<String, Player> Team1 =  Database.loadTeam(Team.TOR,true);
        HashMap<String,Player> Team2 = Database.loadTeam(Team.SFP,false);
        player1 = Team1.get("Bob Bergen");
        player2 = Team2.get("Jonathan Lundberg");
        player2.setHomeTeam(false);
        player2.setX(700);
        a = Arena.getArena();
        puck = new Puck();
    }

    @Override
    public void update() {
        player1.tick();
        player2.tick();
        puck.tick();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);
        player1.render(g);
        player2.render(g);
        puck.render(g);


        //g.drawImage(Assets.homeTeam,500,250,null);
        //g.drawImage(Assets.awayTeam,700,250,null);
//        g.drawLine(a.xFullMin,a.yFullMin,a.xFullMax,a.yFullMin);
//        g.drawLine(a.xFullMin,a.yFullMin,a.xFullMin,a.yFullMax);
//        g.drawLine(a.xFullMin,a.yFullMax,a.xFullMax,a.yFullMax);
//        g.drawLine(a.xFullMax,a.yFullMin,a.xFullMax,a.yFullMax);
//
//        g.drawLine(a.xFullMin,a.yLimitMin,a.xFullMax,a.yLimitMin);
//        g.drawLine(a.xLimitMin,a.yFullMin,a.xLimitMin,a.yFullMax);
//        g.drawLine(a.xFullMin,a.yLimitMax,a.xFullMax,a.yLimitMax);
//        g.drawLine(a.xLimitMax,a.yFullMin,a.xLimitMax,a.yFullMax);




    }
}
