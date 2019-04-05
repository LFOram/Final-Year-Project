package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Player;

import java.util.ArrayList;

public class FaceoffPlayerState implements PlayerState{
    private Player player;
    private ArrayList<Player> teamPlayers = new ArrayList<>();

    public FaceoffPlayerState(Player player) {
        this.player = player;
    }


    @Override
    public void hit(float xOffset, float yOffset) {

    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        //get positions
        teamPlayers = player.getPositions(player.isHomeTeam());
        //get only defensemen

        //pick player to pass to

        //Faceoff



    }

    @Override
    public void act() {

    }
}
