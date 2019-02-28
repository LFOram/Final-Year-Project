package Project.Base;

/**
 * Created by Leon on 16/01/2019.
 */
public class Arena {
    private static Arena instance = null; //Class uses singleton pattern to ensure only one arena instance exists

    //Fields
    //boarders of rink
    public int xFullMin = 104;
    public int yFullMin =52;
    public int xFullMax = xFullMin + 1003;
    public int yFullMax = yFullMin + 428;

    public int xLimitMax = xFullMax - 64;
    public int xLimitMin = xFullMin + 64;

    public int yLimitMax = yFullMax - 64;
    public int yLimitMin = yFullMin + 64;


    private Arena() {
    }

    public boolean onIce(float x,float y){
        //calculate x
        if(x<xFullMin || x>xFullMax ){//outside full rink on x
            System.out.println("outside full rink on x");
            return false;
        }
        if(y>yFullMax || y<yFullMin){//outside full rink on y
            System.out.println("outside full rink on y");
            return false;
        }
        //we know player inside full rink, check for corners
        if(x<xLimitMin || x>xLimitMax){//could be in corners
            if (y<yLimitMin || y>yLimitMax) {//in corner
                //calculate corner
                if(x<xLimitMin && y<yLimitMin){//top left
                    if(Math.pow((x-xLimitMin),2) + Math.pow((y-yLimitMin),2) <=  62){
                        return true;
                    }
                    return false;
                }
                else if(x>xLimitMax && y<yLimitMin){//top Right
                    if(Math.pow((x-xLimitMax),2) + Math.pow((y-yLimitMin),2) <=  62){
                        return true;
                    }
                    return false;
                }
                else if(x<xLimitMin && y>yLimitMax){//Bottom left
                    if(Math.pow((x-xLimitMin),2) + Math.pow((y-yLimitMax),2) <=  62){
                        return true;
                    }
                    return false;
                }
                else if(x>xLimitMax && y>yLimitMax){//Bottom right
                    if(Math.pow((x-xLimitMax),2) + Math.pow((y-yLimitMax),2) <=  62){
                        return true;
                    }
                    return false;
                }
            }
        }
        //not in corner
        return true;
    }

    public static Arena getArena() {
        if(instance == null) {
            instance = new Arena();
        }
        return instance;
    }
}
