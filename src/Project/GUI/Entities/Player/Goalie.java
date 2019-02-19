package Project.GUI.Entities.Player;

import Project.Base.Position;
import Project.Base.Team;

/**
 * Created by Leon on 02/12/2018.
 */
public class Goalie extends Player {

    private Position position = Position.GOALIE;
    private GoalieStats stats;

    public Goalie(PlayerDetails player, GoalieStats stats, Team team, Boolean home) {
        super(player,team,home);
        this.stats = stats;
    }

    public String toString(){
        String string = position.toString() + " - " +  super.toString();
        string += " SK:"+stats.getStatSkating()+" EN:"+stats.getStatEndurance()+" SI:"+stats.getStatSize()+" AG:"+stats.getStatAgility()+" RB:"+stats.getStatReboundControl();
        string += " SC"+stats.getStatStyleControl()+" HS:"+stats.getStatHandSpeed()+" PH:"+stats.getStatPuckHandling()+" PS:"+stats.getStatPenaltyShot();
        return string;
    }
}
