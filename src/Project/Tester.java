package Project;

import Project.Base.Game;
import Project.Base.Team;


/**
 * Created by Leon on 16/01/2019.
 */
public class Tester {

    public static void main(String args[]){
        Game game = new Game(Team.TOR,Team.SFP);
        System.out.println(game.listAllPlayers());
    }
}