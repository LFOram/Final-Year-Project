package Project.Base;

/**
 * Created by Leon on 02/12/2018.
 */
public class Goalie extends Player{

    private Position position = Position.GOALIE;
    private GoalieStats stats;

    public Goalie(PlayerDetails player,GoalieStats stats) {
        super(player);
        this.stats = stats;
    }
}
