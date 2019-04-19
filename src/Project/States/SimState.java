package Project.States;

import Project.Base.*;
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
    public void startGame() {

    }

    @Override
    public void setFaceoffDot(int dot) {

    }

}
