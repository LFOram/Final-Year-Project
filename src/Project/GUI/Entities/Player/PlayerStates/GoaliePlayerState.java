package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Goalie;

public class GoaliePlayerState implements PlayerState {
    Goalie player;

    public GoaliePlayerState(Goalie player) {
        this.player = player;
    }

    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {

    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {

    }

    @Override
    public void act() {

    }
}
