package Project.GUI;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;




/**
 * Created by Leon on 04/02/2019.
 */
public class GUITester extends JFrame{
    private BackgroundPanel panel;

    public GUITester(){
        initUI();

    }

    private void initUI(){
        setTitle("FSTHS");
        setSize(1220,545);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //set layout manager
        //BorderLayout bl = new BorderLayout();
        //setLayout(bl);
        //JLayeredPane lp = new JLayeredPane();
        Container c = getContentPane();
        JPanel p1 = new JPanel();
        OverlayLayout overlay = new OverlayLayout(p1);
        p1.setLayout(overlay);

        //create components

        panel = new BackgroundPanel();
        Dimension d1 = new Dimension(1220,540);
        panel.setMaximumSize(d1);
        PlayerCircle circle1 = new PlayerCircle(Color.BLUE,Color.RED,"22");
        Dimension d2 = new Dimension(50,50);
        circle1.setShapeColor(Color.BLUE);
        circle1.setMaximumSize(d2);
        circle1.setAlignmentX(0.5f);
        circle1.setAlignmentY(0.5f);


        //add

        p1.add(circle1);
        p1.add(panel);
        c.add(p1,"Center");
        //bl.add(lp,BorderLayout.CENTER);
        //lp.add(c,1);


        //lp.add(circle1,3);


    }


    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            var ex = new GUITester();
            ex.setVisible(true);
                }
        );
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        System.out.println(System.getProperty("user.dir"));
        try {
            backgroundImage = ImageIO.read(new File("Final Year Project/IceRinkV1.png"));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
    protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this); // see javadoc for more info on the parameters
        }
}

class PlayerCirclePanel extends JPanel {
    private Color shapeColor;

    protected void paintComponent(Graphics g) {
        //Adding  super.paintComponent....
        super.paintComponent(g);
        //g.setColor(color);

        g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }

    public void setShapeColor(Color color){
        this.shapeColor = color;
    }
}

class PlayerCircle extends PlayerCirclePanel{
    public PlayerCircle(Color colour, Color textColour, String number){
        PlayerCirclePanel panel = new PlayerCirclePanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints cl;
        cl = new GridBagConstraints();
        cl.gridy = 0;
        panel.add(new JLabel("22"), cl);
        panel.setOpaque(false);
    }
}