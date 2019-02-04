package Project;

/**
 * Created by Leon on 04/12/2018.
 */
public class PlayerDetails {
    protected String name;
    protected String number;
    protected int Height;
    protected int weight;
    protected String birthplace;

    public PlayerDetails(String Name, String number, int height, int weight, String birthplace) {
        this.name = Name;
        this.number = number;
        Height = height;
        this.weight = weight;
        this.birthplace = birthplace;
    }
}
