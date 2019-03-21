package Project.States;

import Project.Base.Handler;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;
import java.awt.*;



public class SimState extends State {

    public SimState(Handler handler){
      super(handler);
    }



    @Override
    public void update() {

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
