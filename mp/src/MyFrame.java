import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    MyFrame(){
        ImageIcon mpLogo = new ImageIcon("play-button.png");
        this.setIconImage(mpLogo.getImage());
        this.setSize(1000,600);
        this.setTitle("Music Player");
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        //this.getContentPane().setBackground(Color.DARK_GRAY);
    }

}
