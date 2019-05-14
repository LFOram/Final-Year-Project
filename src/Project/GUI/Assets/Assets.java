package Project.GUI.Assets;

import Project.Base.Database;
import Project.Base.Enums.Team;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Assets {
    public static BufferedImage rink;
    public static SpriteSheet homeSheet,awaySheet;
    public static SpriteSheet homeNumberSheet,awayNumberSheet;
    public static BufferedImage homeTeam,awayTeam;
    public static ArrayList<BufferedImage> homeNumbers = new ArrayList<BufferedImage>(10);
    public static ArrayList<BufferedImage> awayNumbers = new ArrayList<BufferedImage>(10);
    public static BufferedImage numberSpace;
    public static BufferedImage puck;

    private static final int height = 21;
    private static final int width = 21;


    public static void init(){

        rink = ImageLoader.loadImage("/IceRinkV1.png");
        puck = ImageLoader.loadImage("/Puck.png");
        homeSheet = new SpriteSheet(ImageLoader.loadImage("/SpritesheetHomeTeams.png"));
        awaySheet = new SpriteSheet(ImageLoader.loadImage("/SpritesheetAwayTeams.png"));
        homeNumberSheet = new SpriteSheet(ImageLoader.loadImage("/SpritesheetHomeNumbers.png"));
        awayNumberSheet = new SpriteSheet(ImageLoader.loadImage("/SpritesheetAwayNumbers.png"));
        numberSpace = homeNumberSheet.crop(6,0,1,9);
    }


    public static void loadTeamAssets(Team home, Team away){
        int[] position;
        int teamNo;
        BufferedImage image;

        //Home Team
        position = Database.getTeamPosition(home);
        homeTeam = homeSheet.crop((Objects.requireNonNull(position)[0]*width),(position[1]*height),width,height);
        teamNo = Database.getTeamNumber(home);
        for (int i=0;i<10;i++){
            image = homeNumberSheet.crop(i*5,teamNo*9,5,9);
            homeNumbers.add(image);
        }

        //Away team
        position = Database.getTeamPosition(away);
        awayTeam = awaySheet.crop((Objects.requireNonNull(position)[0]*width),(position[1]*height),width,height);
        teamNo = Database.getTeamNumber(away);
        for (int i=0;i<10;i++){
            image = awayNumberSheet.crop(i*5,teamNo*9,5,9);
            awayNumbers.add(image);
        }
    }
}
