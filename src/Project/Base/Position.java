package Project.Base;

/**
 * Created by Leon on 02/12/2018.
 */
public enum Position {
    LWING ("LW"),
    CENTER ("C"),
    RWING ("RW"),
    DEFENCE("D"),
    GOALIE ("G") ;

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
