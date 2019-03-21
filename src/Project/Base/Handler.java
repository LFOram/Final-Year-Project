package Project.Base;

import Project.GUI.Sim;

public class Handler {
    private Sim sim;

    public Handler(Sim sim){
        this.sim = sim;
    }

    public Sim getSim() {
        return sim;
    }

    public void setSim(Sim sim) {
        this.sim = sim;
    }

}
