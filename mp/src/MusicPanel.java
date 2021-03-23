import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MusicPanel extends JPanel {

    JButton playButton;
    JButton nextButton;
    JToggleButton playerModeToggleButton;
    private static final MusicStuff musicObject = new MusicStuff();
    private static JLabel currentSongLabel;
    private static JLabel playerModeLabel;
    private static JLabel nowPlayingLabel;

    MusicPanel() {
        this.setBackground(new Color(0x303030));
        this.setBounds(0, 450, 1000, 150);
        this.setLayout(null);

        Random random = new Random();

        playerModeLabel = new JLabel();
        playerModeLabel.setLocation(860, 60);
        playerModeLabel.setSize(100, 15);
        playerModeLabel.setForeground(Color.white);
        playerModeLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        playerModeLabel.setText(Controller.getPlayerMode().toString());
        this.add(playerModeLabel);

        playerModeToggleButton = new JToggleButton("Mode");
        playerModeToggleButton.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        playerModeToggleButton.setBackground(new Color(0x83d4ec));
        playerModeToggleButton.setBounds(850, 20, 80, 30);
        playerModeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Controller.getPlayerMode() == PlayerMode.ORDERED){
                    Controller.setPlayerMode(PlayerMode.SHUFFLE);
                    refreshModeLobel();
                }
                else{
                    Controller.setPlayerMode(PlayerMode.ORDERED);
                    refreshModeLobel();
                }
            }
        });
        this.add(playerModeToggleButton);
        this.add(playerModeLabel);
        
        nowPlayingLabel = new JLabel("Now playing: ");
        nowPlayingLabel.setLocation(300, 68);
        nowPlayingLabel.setSize(100, 15);
        nowPlayingLabel.setForeground(Color.white);
        nowPlayingLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        this.add(nowPlayingLabel);

        currentSongLabel = new JLabel();
        currentSongLabel.setLocation(400, 68);
        currentSongLabel.setSize(500, 15);
        currentSongLabel.setForeground(Color.white);
        currentSongLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        if(Controller.getCurrentSongPlaying() != null){
            currentSongLabel.setText(Controller.getCurrentSongPlaying().getTitle() + " / "
                + Controller.getCurrentSongPlaying().getArtist().getFirstName() + " "
                + Controller.getCurrentSongPlaying().getArtist().getLastName() + " / "
                + Controller.getCurrentSongPlaying().getAlbum().getTitle());
        }
        this.add(currentSongLabel);

        playButton = new JButton("Play");
        playButton.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        playButton.setBackground(new Color(0x83d4ec));
        playButton.setBounds(400, 20, 80, 30);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.getSongQueue().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Queue is empty, add a song first!");
                } else {
                    if (Controller.getPlayerMode() == PlayerMode.ORDERED) {
                        if (Controller.getPlayerStatus() == PlayerStatus.STOPPED) {
                            String filepath = Controller.getSongQueue().get(0).getMusicpath() + ".wav";
                            Controller.setCurrentSongPlaying(Controller.getSongQueue().get(0));
                            refreshSongLabel();
                            Controller.setPlayerStatus(PlayerStatus.PLAYING);
                            musicObject.playMusic(filepath);
                        } else {
                            String filepath = Controller.getCurrentSongPlaying().getMusicpath() + ".wav";
                            musicObject.playMusic(filepath);
                        }
                    }
                    else{
                        if (Controller.getPlayerStatus() == PlayerStatus.STOPPED){
                            int randomSongId = random.nextInt(Controller.getSongQueue().size() - 1);
                            String filepath = Controller.getSongQueue().get(randomSongId).getMusicpath() + ".wav";
                            Controller.setCurrentSongPlaying(Controller.getSongQueue().get(randomSongId));
                            refreshSongLabel();
                            Controller.setPlayerStatus(PlayerStatus.PLAYING);
                            musicObject.playMusic(filepath);
                        }
                        else{
                            String filepath = Controller.getCurrentSongPlaying().getMusicpath() + ".wav";
                            musicObject.playMusic(filepath);
                        }
                    }
                }
            }
        });
        this.add(playButton);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        nextButton.setBackground(new Color(0x83d4ec));//0x1db954
        nextButton.setBounds(500, 20, 80, 30);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.getSongQueue().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Queue is empty, add a song first!");
                } else {
                    if (Controller.getPlayerStatus() == PlayerStatus.STOPPED) {
                        JOptionPane.showMessageDialog(null, "First play a song!");
                    } else {
                        int currentPlayingSongIndex = 0;
                        if (Controller.getPlayerMode() == PlayerMode.ORDERED) {
                            for (int i = 0; i < Controller.getSongQueue().size(); i++) {
                                if (Controller.getSongQueue().get(i).getId() == Controller.getCurrentSongPlaying().getId()) {
                                    break;
                                }
                                currentPlayingSongIndex++;
                            }
                            System.out.println(currentPlayingSongIndex + " and size " + Controller.getSongQueue().size());
                            if (currentPlayingSongIndex+1 == Controller.getSongQueue().size()) {
                                JOptionPane.showMessageDialog(null, "The queue has been finished, nothing left to play!");
                                Controller.setPlayerStatus(PlayerStatus.STOPPED);
                            }
                            else{
                                Controller.setCurrentSongPlaying(Controller.getSongQueue().get(currentPlayingSongIndex+1));
                                refreshSongLabel();
                            }
                        }
                        else{
                            int randomSongId = random.nextInt(Controller.getSongQueue().size() - 1);
                            Controller.setCurrentSongPlaying(Controller.getSongQueue().get(randomSongId));
                            refreshSongLabel();
                        }
                    }
                }
            }
        });
        this.add(nextButton);

        this.setVisible(true);
    }

    public static void refreshSongLabel(){
        if(Controller.getCurrentSongPlaying() != null){
            currentSongLabel.setText(Controller.getCurrentSongPlaying().getTitle() + " / "
                    + Controller.getCurrentSongPlaying().getArtist().getFirstName() + " "
                    + Controller.getCurrentSongPlaying().getArtist().getLastName() + " / "
                    + Controller.getCurrentSongPlaying().getAlbum().getTitle());
        }
        else{
            currentSongLabel.setText("");
        }
    }

    public static void refreshModeLobel(){
        playerModeLabel.setText(Controller.getPlayerMode().toString());
    }
}

