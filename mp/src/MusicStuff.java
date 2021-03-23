import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class MusicStuff {

    void playMusic(String musicLocation){
        try{
            File musicPath = new File(musicLocation);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                JOptionPane.showMessageDialog(null,"Press ok to stop playing");
                clip.stop();

            }
            else{
                System.out.println("Cannot find file");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
