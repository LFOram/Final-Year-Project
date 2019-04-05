package Project.GUI.Entities.Player.PlayerStates;

public interface PlayerState {
    void hit(float xOffset,float yOffset,float xMove, float yMove);

    void pass();

    void think();

    void act();
}
