package Project.States;

import Project.Base.*;
import Project.Base.Enums.Line;
import Project.Base.Enums.Team;
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
    private Arena a;


    public SimState(Handler handler){
        super(handler);
        Assets.init();
        Database.init();

        a = Arena.getArena();
    }



    @Override
    public void update() {
        for (Player player:handler.getSim().getGame().getAwayTeam().getAllOnIce().values()) {
            player.tick();
        }
        for (Player player:handler.getSim().getGame().getHomeTeam().getAllOnIce().values()){
            player.tick();
        }
        handler.getSim().getGame().getPuck().tick();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);

        for (Player player:handler.getSim().getGame().getAwayTeam().getAllOnIce().values()) {
            player.render(g);
        }
        for (Player player:handler.getSim().getGame().getHomeTeam().getAllOnIce().values()){
            player.render(g);
        }
        handler.getSim().getGame().getPuck().render(g);


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
