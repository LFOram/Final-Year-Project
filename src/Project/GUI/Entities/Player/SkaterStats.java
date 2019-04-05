package Project.GUI.Entities.Player;

import Project.Base.Stats;

/**
 * Created by Leon on 04/12/2018.
 */
public class SkaterStats extends Stats {
    private int statChecking;
    private int statFighting;
    private int statDiscipline;
    private int statSkating;
    private int statStrength;
    private int statEndurance;
    private int statPuckHandling;
    private int statFaceOffs;
    private int statPassing;
    private int statScoring;
    private int statDefence;
    private int statPenaltyShot;

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

    public String toString(){
        String string ="";
        string += " SK:"+statChecking+" FI:"+statFighting+" DI:"+statDiscipline+ " SK:"+statSkating+" ST:"+statStrength+" EN:"+statEndurance;
        string += " PH:"+statPuckHandling+" FO:"+statFaceOffs+" PA:"+statPassing+" SC:"+statScoring+" DF:"+statDefence+" PS:"+statPenaltyShot;
        return string;
    }
}
