package Project.Base;

/**
 * Created by Leon on 16/01/2019.
 */
public class Arena {
    private static Arena instance = null; //Class uses singleton pattern to ensure only one arena instance exists

    //Fields


    private Arena() {
    }

    public static Arena getArena() {
        if(instance == null) {
            instance = new Arena();
        }
        return instance;
    }
}
