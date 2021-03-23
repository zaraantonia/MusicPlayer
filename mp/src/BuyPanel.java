import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class BuyPanel extends JPanel {

    private static Song selectedSong;
    private static Album selectedAlbum;
    JButton buySongButton;
    JButton buyAlbumButton;

    BuyPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<PremiumUser> premiumUsers, ArrayList<StandardUser> standardUsers, ArrayList<User> allUsers, ArrayList<Song> allSongs, ArrayList<Album> allAlbums) {
        this.setBackground(Color.darkGray);
        this.setBounds(0, 0, 850, 400);
        this.setLayout(null);


        //buy song area
        JLabel buySongLabel = new JLabel("Buy a song:");
        buySongLabel.setLocation(20, 10);
        buySongLabel.setSize(150, 15);
        buySongLabel.setForeground(Color.white);
        buySongLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(buySongLabel);

        BuySongSplitPane buySongSplitPane = new BuySongSplitPane(currentStandard, currentPremium, currentArtist, currentUserType, allSongs);

        buySongButton = new JButton("Buy song");
        buySongButton.setHorizontalTextPosition(JButton.CENTER);
        buySongButton.setVerticalTextPosition(JButton.CENTER);
        buySongButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        buySongButton.setBackground(new Color(0x83d4ec));
        buySongButton.setBounds(20, 140, 120, 30);
        buySongButton.setFocusable(false);
        buySongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(selectedSong == null){
                        JOptionPane.showMessageDialog(null,"First select a song!");
                    }
                    else {
                        if (currentUserType == UserType.STANDARD) {
                            boolean found = false;
                            for (Song loopSong : currentStandard.getOwnedSongs()) {
                                if (loopSong.getId() == selectedSong.getId()) {
                                    found = true;
                                    break;
                                }
                            }
                            for (Album loopAlbum: currentStandard.getOwnedAlbums()){
                                if(loopAlbum.getId() == selectedSong.getAlbum().getId()){
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                currentStandard.buySong(selectedSong);
                                PlaySongSplitPane.refreshOwnedSongs(currentUserType, currentStandard, allSongs);
                                int userId = currentStandard.getId();
                                int songId = selectedSong.getId();
                                Database.addNewOwnedSong(myConn, userId, songId);
                                JOptionPane.showMessageDialog(null, "You just bought a song!");
                            } else {
                                JOptionPane.showMessageDialog(null, "You already own this song, enjoy it!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You own all the songs already, enjoy our music!");
                        }
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //buy album area
        JLabel buyAlbumLabel = new JLabel("Buy an album:");
        buyAlbumLabel.setLocation(20, 200);
        buyAlbumLabel.setSize(150, 15);
        buyAlbumLabel.setForeground(Color.white);
        buyAlbumLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(buyAlbumLabel);

        BuyAlbumSplitPane buyAlbumSplitPane = new BuyAlbumSplitPane(currentStandard, currentPremium, currentArtist, currentUserType, allSongs, allAlbums);

        buyAlbumButton = new JButton("Buy album");
        buyAlbumButton.setHorizontalTextPosition(JButton.CENTER);
        buyAlbumButton.setVerticalTextPosition(JButton.CENTER);
        buyAlbumButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        buyAlbumButton.setBackground(new Color(0x83d4ec));
        buyAlbumButton.setBounds(20, 330, 120, 30);
        buyAlbumButton.setFocusable(false);
        buyAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(selectedAlbum == null){
                        JOptionPane.showMessageDialog(null,"First select an album!");
                    }
                    else {
                        if (currentUserType == UserType.STANDARD) {
                            boolean found = false;
                            for (Album loopAlbum : currentStandard.getOwnedAlbums()) {
                                if (loopAlbum.getId() == selectedAlbum.getId()) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                currentStandard.buyAlbum(selectedAlbum);
                                int userId = currentStandard.getId();
                                int albumId = selectedAlbum.getId();
                                Database.addNewOwnedAlbum(myConn, userId, albumId);
                                for (Song loopSong : allSongs) {
                                    if (loopSong.getAlbum() == selectedAlbum) {
                                        if (currentStandard.userOwnsSong(loopSong) == false) {
                                            currentStandard.buySong(loopSong);
                                        }
                                    }
                                }
                                PlaySongSplitPane.refreshOwnedSongs(currentUserType, currentStandard, allSongs);
                                PlayAlbumSplitPane.refreshOwnedAlbums(currentUserType, currentStandard, allAlbums);
                                JOptionPane.showMessageDialog(null, "You just bought an album!");
                            } else {
                                JOptionPane.showMessageDialog(null, "You already own this album, enjoy it!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You own all the albums already, enjoy our music!");
                        }
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        this.add(buyAlbumButton);
        this.add(buySongButton);
        this.add(buyAlbumSplitPane);
        this.add(buySongSplitPane);
        this.setVisible(true);

    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public static void setSelectedSong(Song newSong) {
        selectedSong = newSong;
    }

    public static Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public static void setSelectedAlbum(Album selectedAlbum) {
        BuyPanel.selectedAlbum = selectedAlbum;
    }
}
