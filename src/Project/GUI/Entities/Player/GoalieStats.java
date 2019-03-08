package Project.GUI.Entities.Player;

import Project.Base.Stats;

/**
 * Created by Leon on 04/12/2018.
 */
public class GoalieStats extends Stats {
    protected int statSkating;
    protected int statEndurance;
    protected int statSize;
    protected int statAgility;
    protected int statReboundControl;
    protected int statStyleControl;
    protected int statHandSpeed;
    protected int statPuckHandling;
    protected int statPenaltyShot;

    public GoalieStats(int statSkating, int statEndurance, int statSize, int statAgility, int statReboundControl, int statStyleControl, int statHandSpeed, int statPuckHandling, int statPenaltyShot) {
        this.statSkating = statSkating;
        this.statEndurance = statEndurance;
        this.statSize = statSize;
        this.statAgility = statAgility;
        this.statReboundControl = statReboundControl;
        this.statStyleControl = statStyleControl;
        this.statHandSpeed = statHandSpeed;
        this.statPuckHandling = statPuckHandling;
        this.statPenaltyShot = statPenaltyShot;
    }

    public int getStatSkating() {
        return statSkating;
    }

    public int getStatEndurance() {
        return statEndurance;
    }

    public int getStatSize() {
        return statSize;
    }

    public int getStatAgility() {
        return statAgility;
    }

    public int getStatReboundControl() {
        return statReboundControl;
    }

    public int getStatStyleControl() {
        return statStyleControl;
    }

    public int getStatHandSpeed() {
        return statHandSpeed;
    }

    public int getStatPuckHandling() {
        return statPuckHandling;
    }

    public int getStatPenaltyShot() {
        return statPenaltyShot;
    }
}
