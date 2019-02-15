package Project.GUI;

import Project.Base.Team;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage rink;
    public static BufferedImage homeTeam,awayTeam;


    public static void init(){
        rink = ImageLoader.loadImage("/IceRinkV1.png");
    }

    public void loadTeamAssets(Team home, Team away){

    }

}
