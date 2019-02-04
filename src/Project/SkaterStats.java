package Project;

/**
 * Created by Leon on 04/12/2018.
 */
public class SkaterStats extends Stats {
    protected int statChecking;
    protected int statFighting;
    protected int statDiscipline;
    protected int statSkating;
    protected int statStrength;
    protected int statEndurance;
    protected int statPuckHandling;
    protected int statFaceOffs;
    protected int statPassing;
    protected int statScoring;
    protected int statDefence;
    protected int statPenaltyShot;

    public SkaterStats(int statChecking, int statFighting, int statDiscipline, int statSkating, int statStrength, int statEndurance, int statPuckHandling, int statFaceOffs, int statPassing, int statScoring, int statDefence, int statPenaltyShot) {
        this.statChecking = statChecking;
        this.statFighting = statFighting;
        this.statDiscipline = statDiscipline;
        this.statSkating = statSkating;
        this.statStrength = statStrength;
        this.statEndurance = statEndurance;
        this.statPuckHandling = statPuckHandling;
        this.statFaceOffs = statFaceOffs;
        this.statPassing = statPassing;
        this.statScoring = statScoring;
        this.statDefence = statDefence;
        this.statPenaltyShot = statPenaltyShot;
    }

    public int getStatChecking() {
        return statChecking;
    }

    public int getStatFighting() {
        return statFighting;
    }

    public int getStatDiscipline() {
        return statDiscipline;
    }

    public int getStatSkating() {
        return statSkating;
    }

    public int getStatStrength() {
        return statStrength;
    }

    public int getStatEndurance() {
        return statEndurance;
    }

    public int getStatPuckHandling() {
        return statPuckHandling;
    }

    public int getStatFaceOffs() {
        return statFaceOffs;
    }

    public int getStatPassing() {
        return statPassing;
    }

    public int getStatScoring() {
        return statScoring;
    }

    public int getStatDefence() {
        return statDefence;
    }

    public int getStatPenaltyShot() {
        return statPenaltyShot;
    }
}
