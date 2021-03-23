import javafx.scene.control.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class PlayPanel extends JPanel {

    private static Song selectedSong;
    private static Album selectedAlbum;
    private static JTextArea textArea;
    JButton playSongButton;
    JButton playAlbumButton;
    JButton clearQueueButton;

    PlayPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs, ArrayList<Album> allAlbums) {
        this.setBackground(Color.darkGray);
        this.setBounds(0, 0, 850, 400);
        this.setLayout(null);

        //songs queue display
        textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(450, 10, 150, 300);
        textArea.setForeground(Color.white);
        String temporaryString = "Songs in queue:\n";
        int nrRow = 0;
        for (Song loopSong : Controller.getSongQueue()) {
            if (nrRow == 14) {
                temporaryString = temporaryString.concat("\n   + more...");
            } else {
                temporaryString = temporaryString.concat("   • ");
                temporaryString = temporaryString.concat(loopSong.getTitle());
                temporaryString = temporaryString.concat("\n");
                nrRow++;
            }
        }
        textArea.setText(temporaryString);

        //clear queue button
        clearQueueButton = new JButton("Clear Queue");
        clearQueueButton.setHorizontalTextPosition(JButton.CENTER);
        clearQueueButton.setVerticalTextPosition(JButton.CENTER);
        clearQueueButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        clearQueueButton.setBackground(new Color(0x83d4ec));
        clearQueueButton.setBounds(450, 330, 120, 30);
        clearQueueButton.setFocusable(false);
        clearQueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Controller.getSongQueue().clear();
                    Controller.setCurrentSongPlaying(null);
                    Controller.setPlayerStatus(PlayerStatus.STOPPED);
                    refreshQueueDisplay();
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //play song area
        JLabel playSongLabel = new JLabel("Pick a song:");
        playSongLabel.setLocation(20, 10);
        playSongLabel.setSize(150, 15);
        playSongLabel.setForeground(Color.white);
        playSongLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(playSongLabel);

        PlaySongSplitPane playSongSplitPane = new PlaySongSplitPane(currentStandard, currentPremium, currentArtist, currentUserType, allSongs);

        playSongButton = new JButton("Play Later");
        playSongButton.setHorizontalTextPosition(JButton.CENTER);
        playSongButton.setVerticalTextPosition(JButton.CENTER);
        playSongButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        playSongButton.setBackground(new Color(0x83d4ec));
        playSongButton.setBounds(20, 140, 120, 30);
        playSongButton.setFocusable(false);
        playSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedSong == null) {
                        JOptionPane.showMessageDialog(null, "First select a song!");
                    } else {
                        boolean found = false;
                        for (Song loopSong : Controller.getSongQueue()) {
                            if (loopSong.getId() == selectedSong.getId()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Controller.getSongQueue().add(selectedSong);
                            refreshQueueDisplay();
                        } else {
                            JOptionPane.showMessageDialog(null, "Song is already in queue.");
                        }
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //play album area
        JLabel playAlbumLabel = new JLabel("Pick an album:");
        playAlbumLabel.setLocation(20, 200);
        playAlbumLabel.setSize(150, 15);
        playAlbumLabel.setForeground(Color.white);
        playAlbumLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(playAlbumLabel);

        PlayAlbumSplitPane playAlbumSplitPane = new PlayAlbumSplitPane(currentStandard, currentUserType, allAlbums);

        playAlbumButton = new JButton("Play Later");
        playAlbumButton.setHorizontalTextPosition(JButton.CENTER);
        playAlbumButton.setVerticalTextPosition(JButton.CENTER);
        playAlbumButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        playAlbumButton.setBackground(new Color(0x83d4ec));
        playAlbumButton.setBounds(20, 330, 120, 30);
        playAlbumButton.setFocusable(false);
        playAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedAlbum == null) {
                        JOptionPane.showMessageDialog(null, "First select an album!");
                    } else {
                        for (Song loopSong : selectedAlbum.getSongs()) {
                            boolean found = false;
                            for (Song songInQueue : Controller.getSongQueue())
                                if (loopSong.getId() == songInQueue.getId()) {
                                    found = true;
                                    break;
                                }
                            if (found == false) {
                                Controller.getSongQueue().add(loopSong);
                            }
                        }
                        refreshQueueDisplay();
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        this.add(textArea);
        this.add(clearQueueButton);
        this.add(playSongButton);
        this.add(playAlbumButton);
        this.add(playSongSplitPane);
        this.add(playAlbumSplitPane);
        this.setVisible(true);

    }

    public static Song getSelectedSong() {
        return selectedSong;
    }

    public static void setSelectedSong(Song selectedSong) {
        PlayPanel.selectedSong = selectedSong;
    }

    public static Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public static void setSelectedAlbum(Album selectedAlbum) {
        PlayPanel.selectedAlbum = selectedAlbum;
    }

    public void refreshQueueDisplay() {
        String temporaryString = "Songs in queue:\n\n";
        int nrRow = 0;
        for (Song loopSong : Controller.getSongQueue()) {
            if (nrRow == 14) {
                temporaryString = temporaryString.concat("    + more...");
                break;
            } else {
                temporaryString = temporaryString.concat("   • ");
                temporaryString = temporaryString.concat(loopSong.getTitle());
                temporaryString = temporaryString.concat("\n");
                nrRow++;
            }
        }
        textArea.setText(temporaryString);
        this.add(textArea);
    }
}
