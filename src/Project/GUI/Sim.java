package Project.GUI;

import Project.Base.*;
import Project.GUI.Assets.Assets;
import Project.Base.Enums.Team;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Puck;
import Project.States.GameStartState;
import Project.States.SimState;
import Project.States.State;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Sim implements Runnable{

    private GUITest2 display;
    private Thread thread;

    public int width = 1211;
    public int height = 535;

    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;
    private Handler handler;
    private Arena arena = Arena.getArena();
    public Puck puck = new Puck();
    //private Game game;


    //States
    private State simState;
    private State gameStart;

    //Game Teams
    private TeamObject homeTeam;
    private TeamObject awayTeam;

    public Sim(){
        init();
    }

    private void init(){
        display = new GUITest2();
        Assets.init();
        Database.init();
        Assets.loadTeamAssets(Team.TOR,Team.SFP);
        handler = new Handler(this);

        homeTeam = new TeamObject(Team.TOR,true);
        awayTeam = new TeamObject(Team.SFP,false);


        //States
        State startState = new GameStartState(handler);
        State simState = new SimState(handler);

        //set starting state
        State.setState(startState);
    }

/*    public static void setInitialPosition(HashMap<String, Player> team, Boolean home) {
        float[] position = new float[2];
        float i = 0;
        for(Player player: team.values()){
            position = Positions.getCenterFaceoff(player);
            player.setCurrentPosition(600-(25-(10*i)),40);
            player.setTargetPositionRelative(position[0],position[1],home);
        }
    }*/

    public TeamObject getHomeTeam() {
        return homeTeam;
    }

    public TeamObject getAwayTeam() {
        return awayTeam;
    }

    public String listAllPlayers() {
        String playerList = "";
        playerList = "Home Team: " + homeTeam.getTeam().name()+"\n";
        Iterator<Map.Entry<String, Player>> it1 = homeTeam.getPlayerList().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, Player> pair = it1.next();
            playerList += pair.getValue().toString() +"\n";
        }
        playerList += "\nAway Team: " +awayTeam.getTeam().name()+"\n";
        Iterator<Map.Entry<String, Player>> it2 = awayTeam.getPlayerList().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Player> pair = it2.next();
            playerList += pair.getValue().toString()+"\n";
        }
        return playerList;
    }




    private void update(){
        if(State.getState() != null){
            State.getState().update();
        }

    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //clear screen
        g.clearRect(0,0,width,height);

        //draw items
        if(State.getState()!= null){
            State.getState().render(g);
        }

        bs.show();
        g.dispose();
    }

    public void run(){
        init();

        //Set game frame rate
        int fps = 15;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();



        //GAME LOOP!
        while (running){
            now = System.nanoTime();
            delta += (now-lastTime)/timePerTick;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                delta--;
            }
        }
    }

    public synchronized void start(){
        if (running) {
            return;
        }
         else{
            running = true;
        }
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if (!running){
            return;
        }
        else{
            running = false;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
