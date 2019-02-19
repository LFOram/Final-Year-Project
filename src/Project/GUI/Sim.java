package Project.GUI;

import Project.Base.Database;
import Project.GUI.Entities.Player.Player;
import Project.Base.Team;
import Project.States.SimState;
import Project.States.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Sim implements Runnable{
    private GUITest2 display;

    private Thread thread;

    public int width = 1211;
    public int height = 535;

    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private SpriteSheet sheet;
    private BufferedImage test;

    private BufferedImage test2;
    private BufferedImage test3;

    //States
    private State simState;

    public Sim(){

    }

    private void init(){
        display = new GUITest2();
        Assets.init();
        Database.init();
        //test = ImageLoader.loadImage("/IceRinkV1.png");
        //test2 = ImageLoader.loadImage("/SpritesheetHomeTeams.png");
        //test3 = ImageLoader.loadImage("/NumTest.png");
        //sheet = new SpriteSheet(ImageLoader.loadImage("/Sheet.png"));
        Assets.loadTeamAssets(Team.TOR,Team.SFP);
        HashMap<String, Player> Team1 =  Database.loadTeam(Team.TOR,true);

        simState = new SimState();
        State.setState(simState);
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


        //g.drawImage(sheet.crop(0,0,50,50),200,150,null);

        bs.show();
        g.dispose();
    }

    public void run(){
        init();

        //Set game frame rate
        int fps = 20;
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
