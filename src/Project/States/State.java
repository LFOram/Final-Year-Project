package Project.States;

import Project.Base.Handler;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;

import java.awt.*;

public abstract class State {
    protected Handler handler;

    public State(Handler handler){
        this.handler = handler;
    }

    private static State currentState = null;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }

    public abstract void setFaceoffDot(int dot);

    public void update(){
        for (Player player:handler.getSim().getGame().getAwayTeam().getAllOnIce().values()) {
            player.tick();
        }
        for (Player player:handler.getSim().getGame().getHomeTeam().getAllOnIce().values()){
            player.tick();
        }
        handler.getSim().getGame().getPuck().tick();
    }

    public  void render(Graphics g){
        g.drawImage(Assets.rink,0,0,null);
        for (Player player:handler.getSim().getGame().getAwayTeam().getAllOnIce().values()) {
            player.render(g);
        }
        for (Player player:handler.getSim().getGame().getHomeTeam().getAllOnIce().values()){
            player.render(g);
        }
        handler.getSim().getGame().getPuck().render(g);
    }

    public abstract void startGame();
}
