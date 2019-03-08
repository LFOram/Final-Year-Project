package Project.Base.Enums;

/**
 * Created by Leon on 02/12/2018.
 */
public enum Position {
    LWING ("LW"),
    CENTER ("C"),
    RWING ("RW"),
    DEFENCE("D"),
    LDEFENCE("D"),
    RDEFENCE("D"),
    GOALIE ("G"),
    BENCH ("Bench");

    private String value;

    Position(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public String toString() {
        return this.getValue();
    }
}
