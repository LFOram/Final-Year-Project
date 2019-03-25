package Project.States;

import Project.Base.Arena;
import Project.Base.Database;
import Project.Base.Enums.Line;
import Project.Base.Enums.Team;
import Project.Base.Game;
import Project.Base.Positions;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Puck;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SimState extends State {
    private HashMap<String, Player> playerList;
    private Player player1;
    private Player player2;
    private Puck puck;
    private Arena a;
    private Game game;

    public SimState(){
        Assets.init();
        Database.init();

        a = Arena.getArena();
        puck = new Puck();

        game = new Game(Team.TOR,Team.SFP);
        startGame();


        //HashMap<String, Player> Team1 =  Database.loadTeam(Team.TOR,true);
        //HashMap<String,Player> Team2 = Database.loadTeam(Team.SFP,false);

    }

    private void startGame(){
        game.getHomeTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        game.getHomeTeam().setCurrentGoalie(Line.GOALIE1);

        game.getAwayTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        game.getAwayTeam().setCurrentGoalie(Line.GOALIE1);

        //set initial player positions


    }



    @Override
    public void update() {
        for (Player player:game.getAwayTeam().getAllOnIce().values()) {
            player.tick();
        }
        for (Player player:game.getHomeTeam().getAllOnIce().values()){
            player.tick();
        }
        puck.tick();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);

        for (Player player:game.getAwayTeam().getAllOnIce().values()) {
            player.render(g);
        }
        for (Player player:game.getHomeTeam().getAllOnIce().values()){
            player.render(g);
        }
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
