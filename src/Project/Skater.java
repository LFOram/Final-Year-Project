package Project;

/**
 * Created by Leon on 02/12/2018.
 */
public class Skater extends Player {

    private Position position;
    private SkaterStats stats;

    public Skater(PlayerDetails player, Position position, SkaterStats stats) {
        super(player);
        this.position = position;
        this.stats = stats;
    }
}
