package Project.GUI.Entities.Player;

import Project.Base.Team;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


/**
 * Created by Leon on 02/12/2018.
 */
public abstract class Player extends Entity implements PropertyChangeListener {
    private PlayerDetails player;

    private Point icePosition = new Point(); //x,y representation of position on ice (defaults to 0,0)
    private Boolean onIce = false;
    private Team team;
    private Boolean homeTeam;
    private int delta = 5;
    private int numberOffset;
    private int lastTouch;


    public BufferedImage circle;
    private BufferedImage circleNumber;



    public Player(PlayerDetails player,Team team,Boolean home) {
        super(400,250);
        this.player = player;
        this.team = team;
        this.homeTeam = home;
        createCircle();



    }

    public void setX(int x){
        this.x = x;
    }
    public void setHomeTeam(Boolean home){
        this.homeTeam = home;

    }

    public String getPlayerName(){
        return player.name;
    }



    private void createCircle(){
        if (homeTeam){
            circle = Assets.homeTeam;
            setCircleNumber(Assets.homeNumbers);

        }
        else {
            circle = Assets.awayTeam;
            setCircleNumber(Assets.awayNumbers);
        }
    }

    private void setCircleNumber(ArrayList<BufferedImage> numbers){
        if (player.number.length()==1){
            numberOffset = 7;
            circleNumber = numbers.get(Integer.parseInt(player.number));
        }
        else {
            numberOffset =5;
            String[] array = player.number.split("");
            circleNumber = new BufferedImage(11,9,BufferedImage.TRANSLUCENT);
            circleNumber.createGraphics().drawImage(numbers.get((Integer.parseInt(array[0]))),0,0,null);
            circleNumber.createGraphics().drawImage(Assets.numberSpace,5,0,null);
            circleNumber.createGraphics().drawImage(numbers.get((Integer.parseInt(array[1]))),6,0,null);
        }

    }

    public void setLastTouch(int touch){
        this.lastTouch = touch;
    }

    //observer pattern listener
    public void propertyChange(PropertyChangeEvent evt){
        this.setLastTouch((int)evt.getNewValue());
    }


    @Override
    public void tick() {
        if (x>1107){
            delta = -5;
        }
        else if (x<105){
            delta = 5;
        }
        x += delta;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(circle,(int)x,(int)y,null);
        g.drawImage(circleNumber,(int)x+numberOffset,(int)y+6,null);
    }

    public String toString(){
        String string;
        string = player.name + " #" + player.number + ": ";
        return string;
    }
}
