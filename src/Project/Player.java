package Project;

import java.awt.*;

/**
 * Created by Leon on 02/12/2018.
 */
public class Player {
    private PlayerDetails player;
    private Point position = new Point(); //x,y representation of position on ice (defaults to 0,0)
    private Boolean onIce = false;



    public Player(PlayerDetails player) {

        this.player = player;

    }
}
