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

    public String toString() {
        String string = position.toString() + " - " +  super.toString();
        string += " SK:"+stats.statChecking+" FI:"+stats.statFighting+" DI:"+stats.statDiscipline+ " SK:"+stats.statSkating+" ST:"+stats.statStrength+" EN:"+stats.statEndurance;
        string += " PH:"+stats.statPuckHandling+" FO:"+stats.statFaceOffs+" PA:"+stats.statPassing+" SC:"+stats.statScoring+" DF:"+stats.statDefence+" PS:"+stats.statPenaltyShot;
        return string;
    }

}
