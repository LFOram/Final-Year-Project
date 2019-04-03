package Project.GUI;

import Project.Base.Database;
import Project.Base.Game;
import Project.Base.Handler;
import Project.GUI.Assets.Assets;
import Project.Base.Enums.Team;
import Project.States.GameStartState;
import Project.States.SimState;
import Project.States.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Sim implements Runnable{

    private GUITest2 display;

    private Thread thread;

    public int width = 1211;
    public int height = 535;

    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private Game game;

    //States
    public State simState;
    public State startState;
    private Handler handler;


    //Game Teams

    public Sim(){

    }

    private void init(){
        display = new GUITest2();
        Assets.init();
        Database.init();
        handler = new Handler(this);
        //Start Game
        game = new Game(Team.TOR,Team.SFP);


        startState = new GameStartState(handler);
        simState = new SimState(handler);
        State.setState(startState);
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

    public Game getGame(){
        return game;
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
