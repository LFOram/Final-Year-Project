package Project.GUI.Entities.Player;

import Project.Base.Enums.Position;
import Project.Base.Enums.Possession;
import Project.Base.Enums.Team;
import Project.Base.Game;
import Project.Base.Stats;
import Project.GUI.Assets.Assets;
import Project.GUI.Entities.Entity;
import Project.GUI.Entities.Player.PlayerStates.*;

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

    private Boolean onIce = false;

    private Team team;

    private Boolean homeTeam;
    private int numberOffset;
    private Boolean hasPuck;
    private Possession lastTouch;

    protected int currentEndurance = 100;

    protected PlayerState playerState = null;

    public BufferedImage circle;
    private BufferedImage circleNumber;

    public Player(PlayerDetails player,Team team,Boolean home) {
        super(400,250,21,21);
        this.player = player;
        this.team = team;
        this.homeTeam = home;
        createCircle();
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){this.y = y;}

    public void setHomeTeam(Boolean home){
        this.homeTeam = home;
    }

    public void setCurrentPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void setTargetPositionAbsolute(float x, float y){
        this.targetX = x;
        this.targetY = y;
    }

    public String getPlayerName(){
        return player.name;
    }

    public Position getCurrentPosition(){
        return currentPosition;
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
            //System.out.println(player.name);
            //System.out.println(player.number);
            //System.out.println(numbers.toString());
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

    public void setGame(Game game){
        this.game = game;
        System.out.println(this.game.toString());
        this.game.getPuck().addPropertyChangeListener(this);
    }


    public ArrayList<Player> getPositions(boolean home){
        ArrayList<Player> allPlayers = new ArrayList<>();
        if(home){
            allPlayers.addAll(game.getHomeTeam().getAllOnIce().values());
        }
        else {
            allPlayers.addAll(game.getAwayTeam().getAllOnIce().values());
        }
        allPlayers.remove(this);
        return allPlayers;
    }

    public abstract void faceoff(float x,float y);


    protected void setPlayerState(PlayerState state){
        this.playerState = state;
    }


    public void setLastTouch(Possession touch){
        this.lastTouch = touch;
    }

    //observer pattern listener
    public void propertyChange(PropertyChangeEvent evt){
        this.setLastTouch((Possession) evt.getNewValue());
        this.updateState();
    }

    private void updateState(){
        if (this instanceof Skater) {
            if(hasPuck){
                this.setPlayerState(new HasPuckPlayerState(this));
            }
            else {
                if (lastTouch == Possession.FACEOFF) {
                    this.setPlayerState(new FaceoffPlayerState(this));
                } else if (lastTouch == Possession.HOME){
                    if (homeTeam){
                        this.setPlayerState(new AttackingPlayerState((Skater)this));
                    }
                    else {
                        this.setPlayerState(new DefendingPlayerState(this));
                    }
                } else if (lastTouch == Possession.AWAY) {
                    if (homeTeam){
                        this.setPlayerState(new DefendingPlayerState(this));
                    }
                    else {
                        this.setPlayerState(new AttackingPlayerState((Skater)this));
                    }
                }
            }
        } else {
            //is goalie
        }
    }

    public void setCurrentPlayingPosition(Position position){
        this.currentPosition = position;
    }

    protected float getDirection(){
        var deltaX = targetX - x;
        var deltaY = targetY - y;
        var rad = Math.atan2(deltaY,deltaX);
        return (float) rad;
    }

    public abstract Stats getStats();

    public Game getGame() {
        return game;
    }

    public boolean isHomeTeam(){
        return homeTeam;
    }

    public int getCurrentEndurance(){
        return currentEndurance;
    }

    protected void setHasPuck(boolean hasPuck){
        this.hasPuck = hasPuck;
    }

    public Boolean getHasPuck() {
        return hasPuck;
    }

    @Override
    public void tick() {
        playerState.think();
        playerState.act();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(circle,(int)x,(int)y,null);
        g.drawImage(circleNumber,(int)x+numberOffset,(int)y+6,null);
//        g.setColor(Color.red);
//        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
    }

    public String toString(){
        String string;
        string = player.name + " #" + player.number + ": ";
        return string;
    }
}