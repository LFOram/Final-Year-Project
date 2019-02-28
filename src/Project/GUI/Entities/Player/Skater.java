package Project.GUI.Entities.Player;

import Project.Base.Position;
import Project.Base.Team;

/**
 * Created by Leon on 02/12/2018.
 */
public class Skater extends Player {

    private Position position;
    private SkaterStats stats;

    public Skater(PlayerDetails player, Position position, SkaterStats stats, Team team,Boolean home) {
        super(player,team,home);
        this.position = position;
        this.stats = stats;
    }

    public String toString() {
        String string = position.toString() + " - " +  super.toString();
        string += stats.toString();
        return string;
    }


}
