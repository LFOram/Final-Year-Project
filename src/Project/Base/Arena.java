package Project.Base;

import Project.GUI.Entities.Entity;

import java.util.HashMap;

/**
 * Created by Leon on 16/01/2019.
 */
public class Arena {
    private static Arena instance = null; //Class uses singleton pattern to ensure only one arena instance exists
    private static HashMap<Integer,float[]> zonePositions;
    static {
        zonePositions = new HashMap<>();
        zonePositions.put(1,new float[]{90f,107f});
        zonePositions.put(2,new float[]{190f,107f});
        zonePositions.put(3,new float[]{290f,107f});
        zonePositions.put(4,new float[]{390f,107f});
        zonePositions.put(5,new float[]{490f,107f});
        zonePositions.put(6,new float[]{90f,214f});
        zonePositions.put(7,new float[]{190f,214f});
        zonePositions.put(8,new float[]{290f,214f});
        zonePositions.put(9,new float[]{390f,214f});
        zonePositions.put(10,new float[]{490f,214f});
        zonePositions.put(11,new float[]{90f,321f});
        zonePositions.put(12,new float[]{190f,321f});
        zonePositions.put(13,new float[]{290f,321f});
        zonePositions.put(14,new float[]{390f,321f});
        zonePositions.put(15,new float[]{490f,321f});
    }

    private static HashMap<Integer,Integer[]> offZoneWeights;
    //Passing, Shooting, Skating = 100.
    static {
        offZoneWeights = new HashMap<>();
        offZoneWeights.put(1,new Integer[]{25,5,70});
        offZoneWeights.put(2,new Integer[]{30,30,40});
        offZoneWeights.put(3,new Integer[]{35,45,20});
        offZoneWeights.put(4,new Integer[]{45,35,20});
        offZoneWeights.put(5,new Integer[]{70,5,25});
        offZoneWeights.put(6,new Integer[]{25,5,70});
        offZoneWeights.put(7,new Integer[]{20,50,30});
        offZoneWeights.put(8,new Integer[]{15,80,5});
        offZoneWeights.put(9,new Integer[]{10,90,0});
        offZoneWeights.put(10,new Integer[]{75,0,25});
        offZoneWeights.put(11,new Integer[]{25,5,70});
        offZoneWeights.put(12,new Integer[]{30,30,40});
        offZoneWeights.put(13,new Integer[]{35,45,20});
        offZoneWeights.put(14,new Integer[]{45,35,20});
        offZoneWeights.put(15,new Integer[]{70,5,25});
    }

    //Fields
    //boarders of rink
    private static int xFullMin = 104;
    private static int yFullMin =52;
    private static int xFullMax = xFullMin + 1003;
    private static int yFullMax = yFullMin + 428;

    private static int xLimitMax = xFullMax - 64;
    private static int xLimitMin = xFullMin + 64;

    private static int yLimitMax = yFullMax - 64;
    private static int yLimitMin = yFullMin + 64;


    private Arena() {
    }

    public static boolean onIce(int x,int y){
        //calculate x
        if(x<xFullMin || x>xFullMax ){//outside full rink on x
            //System.out.println(x);
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
    public int getHalf(boolean home,float x){
        if(home){
            if(x <= 605){
                return 1;
            }
            else return 2;
        }
        else{
            if (x <= 605){
                return 2;
            }
            else return 2;
        }
    }

    private int getXZone(float x){
        if(x<125){
            return 1;
        }
        else if(x<240){
            return 2;
        }
        else if(x<350){
            return 3;
        }
        else if(x<455){
            return 4;
        }
        else {
            return 5;
        }
    }

    public int getZone(float x, float y){
        x = Math.abs((x-104) - 605);
        y = y-52;

        int xZone = getXZone(x);
        if (y<160){//Zone 1-5
            switch (xZone){
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
            }
        }
        if(y<265){//Zone 6-10
            switch (xZone){
                case 1:
                    return 6;
                case 2:
                    return 7;
                case 3:
                    return 8;
                case 4:
                    return 9;
                case 5:
                    return 10;
            }
        }
        else{//Zone 11-15
            switch (xZone){
                case 1:
                    return 11;
                case 2:
                    return 12;
                case 3:
                    return 13;
                case 4:
                    return 14;
                case 5:
                    return 15;
            }
        }
        return 0;
    }

    public Boolean legalMove(float tx1, float tx2, float ty1, float ty2){
        if (onIce((int) tx1, (int)ty1)&& onIce((int) tx2, (int)ty1)&&onIce((int) tx1, (int)ty2)&&onIce((int) tx2, (int)ty2)){
            return true;
        }
        else return false;
    }

    /**
     *
     * @param x
     * @param y
     * @return 1: y bounce, 2: x bounce, 3: corner bounce
     */
    public int getBounce(float x,float y){
        if(x<xLimitMin || x>xLimitMax) {//could be in corners
            if (y < yLimitMin || y > yLimitMax) {//in corner
                return 3;
            }
            else {//x bounce
                return 2;
            }
        }
        else return 1;
    }

    public  float[] getZonePosition(int zone){
        return zonePositions.get(zone);
    }

    public int getThird(Entity entity){
        if(entity.getX()<378){
            return 1;
        }
        else if(entity.getX()< 628){
            //neutral zone
            return 2;
        }
        else{
            return 3;
        }
    }

    public static Arena getArena() {
        if(instance == null) {
            instance = new Arena();
        }
        return instance;
    }

    public Integer[] getOffensiveZoneWeight(int zone){
        return offZoneWeights.get(zone);
    }

}
