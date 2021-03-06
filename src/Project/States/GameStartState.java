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
        ticks = 0;
    }

    public void startGame(){
        handler.getSim().getGame().getHomeTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        handler.getSim().getGame().getHomeTeam().setCurrentGoalie(Line.GOALIE1);

        handler.getSim().getGame().getAwayTeam().setLines(Line.FORWARD1,Line.DEFENCE1);
        handler.getSim().getGame().getAwayTeam().setCurrentGoalie(Line.GOALIE1);

        setInitialPosition(handler.getSim().getGame().getHomeTeam().getAllOnIce(),true);
        setInitialPosition(handler.getSim().getGame().getAwayTeam().getAllOnIce(),false);

        handler.getSim().setFaceoffState(0);
    }

    public static void setInitialPosition(HashMap<String, Player> team, Boolean home) {
        float[] position;
        float i = 0;
        for(Player player: team.values()){
            position = Positions.getCenterFaceoff(player);
            if(home){
                player.setCurrentPosition((600-25-(5*i)),60);
            }
            else {
                player.setCurrentPosition((600+25+(15*i)),60);
            }
            player.setTargetPositionRelative(position[0],position[1],home);
            i++;
        }
    }

    @Override
    public void setFaceoffDot(int dot) {

    }

    @Override
    public void update() {
//        for (Player player:handler.getSim().getGame().getAwayTeam().getAllOnIce().values()) {
//            player.tick();
//        }
//        for (Player player:handler.getSim().getGame().getHomeTeam().getAllOnIce().values()){
//            player.tick();
//        }
//        handler.getSim().getGame().getPuck().tick();
//        ticks += 1;
//        if (ticks>=250){
//            float[] position = Positions.puckCenterFaceoff();
//            handler.getSim().getGame().getPuck().setPositionRelative(position[0],position[1],false);
//            State.setState(handler.getSim().simState);
//        }
    }

    @Override
    public void render(Graphics g) {

    }
}


