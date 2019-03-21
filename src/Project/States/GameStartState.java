package Project.States;

import Project.Base.Enums.Line;
import Project.Base.Handler;
import Project.Base.Positions;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;

import java.awt.*;
import java.util.HashMap;

public class GameStartState extends State {
    private int ticks;
    private State simState;

    public GameStartState(Handler handler){
        super(handler);
        startGame();
        ticks = 0;
    }

    private void startGame(){
        handler.getSim().getHomeTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        handler.getSim().getHomeTeam().setCurrentGoalie(Line.GOALIE1);

        handler.getSim().getAwayTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        handler.getSim().getAwayTeam().setCurrentGoalie(Line.GOALIE1);

        setInitialPosition(handler.getSim().getHomeTeam().getAllOnIce(),true);
        setInitialPosition(handler.getSim().getAwayTeam().getAllOnIce(),false);
    }

    public static void setInitialPosition(HashMap<String, Player> team, Boolean home) {
        float[] position = new float[2];
        float i = 0;
        for(Player player: team.values()){
            position = Positions.getCenterFaceoff(player);
            player.setCurrentPosition(600-(25-(10*i)),40);
            player.setTargetPositionRelative(position[0],position[1],home);
            i++;
        }
    }



    @Override
    public void update() {
        for (Player player:handler.getSim().getAwayTeam().getAllOnIce().values()) {
            player.tick();
        }
        for (Player player:handler.getSim().getHomeTeam().getAllOnIce().values()){
            player.tick();
        }
        handler.getSim().puck.tick();
        ticks += 1;
        if (ticks>=250){
            float[] position = Positions.puckCenterFaceoff();
            handler.getSim().puck.setPositionRelative(position[0],position[1]);
            State.setState(simState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rink,0,0,null);
        for (Player player:handler.getSim().getAwayTeam().getAllOnIce().values()) {
            player.render(g);
        }
        for (Player player:handler.getSim().getHomeTeam().getAllOnIce().values()){
            player.render(g);
        }
        handler.getSim().puck.render(g);
    }
}


