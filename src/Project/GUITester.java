package Project;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;




/**
 * Created by Leon on 04/02/2019.
 */
public class GUITester extends JFrame{
    private FrameTest panel;

    public GUITester(){
        initUI();

    }

    private void initUI(){
        setTitle("FSTHS");
        setSize(1220,545);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //set layout manager
        setLayout(new BorderLayout());


        //create components
        panel = new FrameTest();

        //add
        Container c = getContentPane();
        c.add(panel,BorderLayout.CENTER);
    }


    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            var ex = new GUITester();
            ex.setVisible(true);
                }
        );
    }
}

class FrameTest extends JPanel {
    private Image backgroundImage;

    public FrameTest(){
        System.out.println(System.getProperty("user.dir"));
        try {
            backgroundImage = ImageIO.read(new File("Final Year Project/IceRinkV1.png"));
        }
            catch(IOException e) {
            System.out.println("File not found");
        }

}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }

}
