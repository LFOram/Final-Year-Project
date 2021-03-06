package Project.States;

import Project.Base.Enums.Position;
import Project.Base.Handler;
import Project.Base.Positions;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Puck;

import java.awt.*;

public class FaceoffState extends State {
    private int faceoffDot;
    private Puck puck;
    private int dotX;
    private int dotY;
    private int timer;

    public FaceoffState(Handler handler) {
        super(handler);
        puck = handler.getSim().getGame().getPuck();
    }

    public void setFaceoffDot(int dot){
        //start faceoff timer (250 ticks)
        System.out.println("I'm here");
        timer = 0;
        //hide puck
        puck.setPositionAbsolute(5,5);
        //puck.setSpeed(0);
        faceoffDot = dot;
        switch (dot){
            case 0:
                dotX = 501;
                dotY=268;
                break;
            case 1:
                dotX =256;
                dotY=153;
                break;
            case 2:
                dotX =512;
                dotY=153;
                break;
            case 3:
                dotX =700;
                dotY=153;
                break;
            case 4:
                dotX =956;
                dotY=153;
                break;
            case 5:
                dotX =256;
                dotY=380;
                break;
            case 6:
                dotX =512;
                dotY=380;
                break;
            case 7:
                dotX =700;
                dotY=380;
                break;
            case 8:
                dotX =956;
                dotY=380;
                break;
            }
            //puck.setPositionAbsolute(dotX,dotY);
        for (Player p:handler.getSim().getGame().getBothTeamOnIce()){
            if (faceoffDot == 0){
                p.faceoff(Positions.getCenterFaceoff(p)[0],Positions.getCenterFaceoff(p)[1]); //double call for no reason.. oh well
            }
            else{
                float[] offset = Positions.getFaceoffOffset(p);
                p.faceoff(dotX+offset[0],dotY+offset[1]);
            }
            //p.faceoff(dotX,dotY);
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void update() {
        super.update();
        System.out.println(timer);

        handler.getSim().getGame().tickFaceoffTimer();

        if(handler.getSim().getGame().getFaceoffTimer()>220){
            //puck.setPositionAbsolute(600,255);
            //puck.setSpeed(0);
            for(Player p:handler.getSim().getGame().getBothTeamOnIce()){
                if (p.getCurrentPosition() == Position.CENTER) {
                }
            }
            System.out.println(dotX + " " + dotY);
            }
        }
}

