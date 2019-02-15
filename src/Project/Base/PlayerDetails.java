package Project.Base;

/**
 * Created by Leon on 04/12/2018.
 */
public class PlayerDetails {
    private String Name;
    private Position position;
    private String number;
    private String Height;
    private String weight;
    private String birthplace;

    public PlayerDetails(String Name, String number, String height, String weight, String birthplace) {
        this.Name = Name;
        this.number = number;
        Height = height;
        this.weight = weight;
        this.birthplace = birthplace;
    }
}
