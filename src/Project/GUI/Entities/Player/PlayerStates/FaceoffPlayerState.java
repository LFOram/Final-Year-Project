package Project.GUI.Entities.Player.PlayerStates;

import Project.Base.Enums.Position;
import Project.Base.Enums.Possession;
import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FaceoffPlayerState implements PlayerState{
    private Player player;
    private SkaterStats playerStats;
    private ArrayList<Player> teamPlayers = new ArrayList<>();
    private ArrayList<Player> defenders = new ArrayList<>();

    private int faceoffRoll;

    public FaceoffPlayerState(Skater player) {
        this.player = player;
        this.playerStats = player.getStats();
    }

    private void faceoffRoll(){
        faceoffRoll = playerStats.getStatFaceOffs()+player.getGame().random(50);
        player.setFaceoffRoll(faceoffRoll);
    }

    private void faceoff(){
        //get positions
        teamPlayers = player.getPositions(player.isHomeTeam());
        //get only defensemen
        for (Player p:teamPlayers){
            if (p.getCurrentPosition() == Position.LDEFENCE || p.getCurrentPosition() == Position.RDEFENCE || p.getCurrentPosition() == Position.DEFENCE ){
                defenders.add(p);
            }
        }

        //pick player to pass to
        Random rand = new Random();
        int rint = rand.nextInt(defenders.size());
        Player playerToPass = defenders.get(rint);

        //Faceoff
        System.out.println("FACEOFF GO");
        player.getGame().getPuck().setPositionAbsolute(600,255);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int oppFaceoffRoll = player.getGame().getOtherCenter(player.isHomeTeam()).getFaceoffRoll();
        //System.out.println("Sleep!");
        //TimeUnit.MILLISECONDS.sleep();
        System.out.println(faceoffRoll + oppFaceoffRoll);
        if(faceoffRoll>oppFaceoffRoll) {
            System.out.println("Passing to " +playerToPass.toString() );
            player.pass(playerToPass);
            player.getGame().getPuck().setLastTouch(Possession.LOOSE);
            player.setState(0);
        }
    }

    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {

    }


    @Override
    public void pass() {

    }

    @Override
    public void think() {
        if(player.getGame().getFaceoffTimer() > 225){
            if (player.getGame().getFaceoffTimer()> 226) {
                if (player.getCurrentPosition()==Position.CENTER) {
                    ((Skater)player).resetFaceoffTimer();
                    faceoff();
                }
            } else {
                faceoffRoll();
            }

        }
    }

    @Override
    public void act() {

    }
}
