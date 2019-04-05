package Project.Base;

import Project.GUI.Entities.Player.Player;

import java.util.HashMap;
import java.util.Map;

public class Positions {

    public static float[] getCenterFaceoff(Player player){
        float[] position = new float[2];
        switch (player.getCurrentPosition()){
            case CENTER:
                position[0] = 10;
                position[1] = 255;
                break;
            case LWING:
                position[0] = 15;
                position[1] = 420;
                break;
            case RWING:
                position[0] = 15;
                position[1] = 85;
            case LDEFENCE:
                position[0] = 115;
                position[1] = 385;
                break;
            case RDEFENCE:
                position[0] = 115;
                position[1] = 120;
                break;
            case GOALIE:
                position[0] = 435;
                position[1] = 255;
        }
        return position;
    }

    public static float[] getFaceoffOffset(Player player){
        float[] position = new float[2];
        switch (player.getCurrentPosition()){
            case CENTER:
                position[0] = 10;
                position[1] = 0;
                break;
            case LWING:
                position[0] = -80;
                position[1] = 5;
                break;
            case RWING:
                position[0] = 80;
                position[1] = 5;
            case LDEFENCE:
                position[0] = -115;
                position[1] = 95;
                break;
            case RDEFENCE:
                position[0] = 115;
                position[1] = 95;
                break;
        }
        if (!player.isHomeTeam()){
            position[0] = -position[0];
            //position[1] = -position[1];
        }
        return position;
    }

    public static float[] puckCenterFaceoff(){
        float[] position = new float[2];
        position[0] = 0;
        position[1] = 255;
        return position;
    }


}
